package me.wappen.neuralcreatures.misc;

import processing.core.PVector;

import java.util.*;
import java.util.function.Supplier;

public class ChunkSpace<T> implements Space<T> {
    private final float chunkSize;
    private final Map<Chunk.Pos, Chunk<T>> chunks;
    private final Map<T, Chunk<T>> objects;

    public ChunkSpace(float chunkSize) {
        this.chunkSize = chunkSize;
        this.chunks = new HashMap<>();
        this.objects = new HashMap<>();
    }

    @Override
    public void addObj(T obj, Supplier<PVector> pos) {
        PVector p = pos.get();
        Chunk<T> chunk = createChunkAt((int) (p.x / chunkSize), (int) (p.y / chunkSize));
        chunk.objects.put(obj, pos);
        objects.put(obj, chunk);
    }

    @Override
    public void removeObj(T obj) {
        Chunk<T> chunk = objects.remove(obj);
        chunk.objects.remove(obj);
    }

    @Override
    public Collection<T> getClosest(PVector pos) {
        int chunkX = (int) (pos.x / chunkSize);
        int chunkY = (int) (pos.y / chunkSize);
        return getClosest(chunkX, chunkY);
    }

    private Collection<T> getClosest(int chunkX, int chunkY) {
        Collection<T> objects = new HashSet<>();

        for (int x = -1; x < 1; x++) {
            for (int y = -1; y < 1; y++) {
                Chunk<T> chunk = chunkAt(chunkX + x, chunkY + y);
                if (chunk != null)
                    objects.addAll(chunk.getObjects());
            }
        }
        return objects;
    }

    @Override
    public Collection<T> getBetween(PVector start, PVector end) {
        int chunkStartX = (int) (start.x / chunkSize);
        int chunkStartY = (int) (start.y / chunkSize);
        int chunkEndX = (int) (end.x / chunkSize);
        int chunkEndY = (int) (end.y / chunkSize);

        int diffX = Math.abs(chunkStartX - chunkEndX);
        int diffY = Math.abs(chunkStartY - chunkEndY);

        Set<PVector> chunks = new HashSet<>();

        float slopeX = (float)diffX / (float)diffY;
        float slopeY = (float)diffY / (float)diffX;

        {
            float y = chunkStartY;
            for (int x = 0; x < diffX; x++) {
                chunks.add(new PVector(x + chunkStartX, y + chunkStartY));
                y += slopeY;
            }
        }

        {
            float x = chunkStartX;
            for (int y = 0; y < diffY; y++) {
                chunks.add(new PVector(x + chunkStartX, y + chunkStartY));
                x += slopeX;
            }
        }

        Collection<T> objects = new HashSet<>();

        for (PVector chunk : chunks)
            objects.addAll(getClosest((int)chunk.x, (int)chunk.y));

        return objects;
    }

    private Chunk<T> createChunkAt(int x, int y) {
        Chunk.Pos p = new Chunk.Pos(x, y);
        if (chunks.containsKey(p))
            return chunks.get(p);

        Chunk<T> chunk = new Chunk<>();
        chunks.put(p, chunk);
        return chunk;
    }

    private Chunk<T> chunkAt(int x, int y) {
        Chunk.Pos p = new Chunk.Pos(x, y);
        if (chunks.containsKey(p))
            return chunks.get(p);
        return null;
    }

    @Override
    public void update() {
        Map<Chunk.Pos, Chunk<T>> newChunks = new HashMap<>();

        for (Map.Entry<Chunk.Pos, Chunk<T>> posChunkEntry : chunks.entrySet()) {
            Chunk<T> chunk = posChunkEntry.getValue();

            List<T> toRemove = new ArrayList<>();

            // Iterate over all objects to move them between chunks
            for (Map.Entry<T, Supplier<PVector>> supplierEntry : chunk.objects.entrySet()) {
                T obj = supplierEntry.getKey();
                PVector pos = supplierEntry.getValue().get();
                Chunk.Pos chunkPos = new Chunk.Pos((int) (pos.x / chunkSize), (int) (pos.y / chunkSize));
                Chunk<T> newChunk = chunkAt(chunkPos.x, chunkPos.y);

                // Check if they moved out of any existent chunks
                if (newChunk == null) {
                    if (newChunks.containsKey(chunkPos)) { // Entity will be inside chunk that is already being created
                        newChunk = newChunks.get(chunkPos);
                        newChunk.objects.put(obj, supplierEntry.getValue());
                        objects.put(obj, newChunk);
                    }
                    else { // Create new chunk
                        newChunk = new Chunk<>();
                        newChunk.objects.put(obj, supplierEntry.getValue());
                        objects.put(obj, newChunk);
                        newChunks.put(chunkPos, newChunk);
                    }
                }
                else if (newChunk != chunk) { // Entity moved to different but existent chunk
                    toRemove.add(obj);
                    newChunk.objects.put(obj, supplierEntry.getValue());
                    objects.put(obj, newChunk);
                }
            }

            for (T obj : toRemove)
                chunk.objects.remove(obj);
        }

        // Remove all empty chunks
        List<Chunk.Pos> toRemove = new ArrayList<>();

        for (Map.Entry<Chunk.Pos, Chunk<T>> entry : chunks.entrySet()) {
            Chunk.Pos pos = entry.getKey();
            Chunk<T> chunk = entry.getValue();
            if (chunk.objects.isEmpty())
                toRemove.add(pos);
        }

        for (Chunk.Pos pos : toRemove)
            chunks.remove(pos);

        // Add all newly created chunks
        chunks.putAll(newChunks);
    }

    private static class Chunk<T> {
        Map<T, Supplier<PVector>> objects = new HashMap<>();

        Collection<T> getObjects() {
            return objects.keySet();
        }

        static class Pos {
            int x;
            int y;

            Pos(int x, int y) {
                this.x = x;
                this.y = y;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Pos pos = (Pos) o;
                return x == pos.x && y == pos.y;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }
        }
    }
}
