package net.snackbag.tt20.util;

import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class RegistryIndex {
    private static final HashMap<RegistryNamespaced, RegistryIndex> indexes = new HashMap<>();

    private final RegistryNamespaced registry;
    private final List<ResourceLocation> identifiers;
    private final HashMap<String, List<ResourceLocation>> namespaceIndex;
    private final HashMap<String, List<ResourceLocation>> pathIndex;

    private RegistryIndex(RegistryNamespaced registry) {
        this.registry = registry;

        this.identifiers = new ArrayList<>();
        this.namespaceIndex = new HashMap<>();
        this.pathIndex = new HashMap<>();

        for (Object keyObj : registry.getKeys()) {
            String key = (String) keyObj;
            ResourceLocation identifier = new ResourceLocation(key);

            String namespace = identifier.getResourceDomain();
            String path = identifier.getResourcePath();

            identifiers.add(identifier);

            namespaceIndex.computeIfAbsent(namespace, k -> new ArrayList<>()).add(identifier);
            pathIndex.computeIfAbsent(path, k -> new ArrayList<>()).add(identifier);
        }
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

    public static RegistryIndex getIndex(RegistryNamespaced registry) {
        if (indexes.containsKey(registry)) {
            return indexes.get(registry);
        }

        RegistryIndex index = new RegistryIndex(registry);
        indexes.put(registry, index);
        return index;
    }
}