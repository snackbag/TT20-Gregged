package net.snackbag.tt20.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistryIndex {
    private static final HashMap<IForgeRegistry<?>, RegistryIndex> indexes = new HashMap<>();

    private final IForgeRegistry<?> registry;
    private final List<ResourceLocation> identifiers;
    private final List<String> namespaces;
    private final List<String> paths;
    private final HashMap<String, List<ResourceLocation>> namespaceIndex;
    private final HashMap<String, List<ResourceLocation>> pathIndex;

    private RegistryIndex(IForgeRegistry<?> registry) {
        this.registry = registry;

        this.identifiers = new ArrayList<>();
        this.namespaces = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.namespaceIndex = new HashMap<>();
        this.pathIndex = new HashMap<>();

        for (ResourceLocation identifier : registry.getKeys()) {
            String namespace = identifier.getResourceDomain();
            String path = identifier.getResourcePath();

            if (!namespaces.contains(namespace)) {
                namespaces.add(namespace);
                namespaceIndex.put(namespace, new ArrayList<>());
            }

            if (!paths.contains(path)) {
                paths.add(path);
                pathIndex.put(path, new ArrayList<>());
            }

            this.identifiers.add(identifier);
            namespaceIndex.get(namespace).add(identifier);
            pathIndex.get(path).add(identifier);
        }
    }

    public IForgeRegistry<?> getRegistry() {
        return registry;
    }

    public List<ResourceLocation> getIdentifiers() {
        return identifiers;
    }

    public HashMap<String, List<ResourceLocation>> getNamespaceIndex() {
        return new HashMap<>(namespaceIndex);
    }

    public HashMap<String, List<ResourceLocation>> getPathIndex() {
        return new HashMap<>(pathIndex);
    }

    public List<String> getNamespaces() {
        return new ArrayList<>(namespaces);
    }

    public List<String> getPaths() {
        return new ArrayList<>(paths);
    }

    public static RegistryIndex getIndex(IForgeRegistry<?> registry) {
        if (indexes.containsKey(registry)) {
            return indexes.get(registry);
        }

        RegistryIndex index = new RegistryIndex(registry);
        indexes.put(registry, index);
        return index;
    }
}
