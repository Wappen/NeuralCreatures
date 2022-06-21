package me.wappen.neuralcreatures.entities.creature.genetic.genes;

import me.wappen.neuralcreatures.entities.creature.genetic.CreaturePrototype;
import me.wappen.neuralcreatures.genetic.Gene;
import me.wappen.neuralcreatures.misc.Graph;
import me.wappen.neuralcreatures.neural.Axon;
import me.wappen.neuralcreatures.neural.NeuralNetwork;
import me.wappen.neuralcreatures.neural.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrainGene implements Gene<CreaturePrototype> {
    List<Neuron> neurons;
    List<Edge> edges;
    Graph<Neuron, Axon> graph;

    Random rng;

    private record Edge(int indexFrom, int indexTo, Axon axon) {}

    public BrainGene() {
        neurons = new ArrayList<>();
        edges = new ArrayList<>();
        graph = new Graph<>();

        rng = new Random();
    }

    @Override
    public void applyTo(CreaturePrototype prototype) {
        int numInputs = prototype.getSenses().getResolution();
        int numOutputs = prototype.getMuscles().getResolution();

        for (int i = 0; i < numInputs + numOutputs + 5; i++) {
            addNeuron(Math.random());
        }

        for (int i = 0; i < numOutputs + 10; i++) {
            int indexFrom = rng.nextInt(0, neurons.size() / 2);
            int indexTo = rng.nextInt(neurons.size() / 2, neurons.size());

            addAxon(indexFrom, indexTo, Math.random());
        }

        NeuralNetwork brain = new NeuralNetwork(graph,
                neurons.subList(0, numInputs),
                neurons.subList(neurons.size() - numOutputs, neurons.size()));

        prototype.setBrain(brain);
    }

    @Override
    public int deserialize(long[] arr, int index) {
        for (int i = index; i < arr.length; i++) {
            try {
                if (arr[i] == 0) { // Neuron
                    addNeuron(Double.longBitsToDouble(arr[++i]));
                } else if (arr[i] == 1) { // Axon
                    int indexFrom = (int) arr[++i];
                    int indexTo = (int) arr[++i];

                    addAxon(indexFrom, indexTo, Double.longBitsToDouble(arr[++i]));
                }
                else {
                    return i - index;
                }
            }
            catch (IndexOutOfBoundsException ignored) {}
        }

        return arr.length - index;
    }

    private void addNeuron(double bias) {
        Neuron neuron = new Neuron(bias, Math::tanh);

        neurons.add(neuron);
        graph.addObj(neuron);
    }

    private void addAxon(int from, int to, double weight) {
        Axon axon = new Axon(weight);

        edges.add(new Edge(from, to, axon));
        graph.addEdge(neurons.get(from), neurons.get(to), axon);
    }

    @Override
    public long[] serialize() {
        List<Long> code = new ArrayList<>();

        for (Neuron neuron : neurons) {
            code.add(0L);
            code.add(Double.doubleToLongBits(neuron.bias()));
        }

        for (Edge edge : edges) {
            code.add(1L);
            code.add((long) edge.indexFrom);
            code.add((long) edge.indexTo);
            code.add(Double.doubleToLongBits(edge.axon.weight()));
        }

        return code.stream().mapToLong(l -> l).toArray();
    }
}
