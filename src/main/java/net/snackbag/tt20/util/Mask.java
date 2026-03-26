package net.snackbag.tt20.util;

import com.google.gson.JsonElement;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.snackbag.tt20.config.JSONConfiguration;

import java.util.*;

public class Mask {
    private final JSONConfiguration file;
    private final MaskType maskType;
    private final RegistryNamespaced registry;
    private final RegistryIndex index;
    private final Set<ResourceLocation> entries;

    public Mask(RegistryNamespaced registry, JSONConfiguration file, String maskKey) {
        this.file = file;
        this.maskType = MaskType.fromString(file.getAsString("type"));
        this.registry = registry;
        this.index = RegistryIndex.getIndex(this.registry);
        this.entries = new HashSet<>();

        for (JsonElement element : file.getAsArray(maskKey)) {
            if (!(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())) {
                continue;
            }

            entries.addAll(manageEntry(element.getAsString()));
        }
    }

    public List<ResourceLocation> manageEntry(String entry) {
        String[] split = entry.split(":");

        if (split.length != 2) return new ArrayList<>();

        if (split[0].equals("*") && split[1].equals("*")) {
            return index.getIdentifiers();
        }

        if (!split[0].equals("*") && !split[1].equals("*")) {
            return Collections.singletonList(new ResourceLocation(split[0], split[1]));
        }

        if (split[0].equals("*")) {
            return index.getPathIndex().getOrDefault(split[1], new ArrayList<>());
        }

        if (split[1].equals("*")) {
            return index.getNamespaceIndex().getOrDefault(split[0], new ArrayList<>());
        }

        return new ArrayList<>();
    }

    public boolean matches(ResourceLocation identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(ResourceLocation identifier) {
        return maskType == MaskType.WHITELIST
                ? entries.contains(identifier)
                : !entries.contains(identifier);
    }
}