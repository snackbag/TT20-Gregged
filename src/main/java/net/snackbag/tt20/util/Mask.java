package net.snackbag.tt20.util;

import com.google.gson.JsonElement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.snackbag.tt20.TT20;
import net.snackbag.tt20.config.JSONConfiguration;

import java.util.*;

public class Mask {
    private final JSONConfiguration file;
    private final MaskType maskType;
    private final IForgeRegistry<?> registry;
    private final RegistryIndex index;
    private final Set<ResourceLocation> entries;

    public Mask(IForgeRegistry<?> registry, JSONConfiguration file, String maskKey) {
        this.file = file;
        this.maskType = MaskType.fromString(file.getAsString("type"));
        this.registry = registry;
        this.index = RegistryIndex.getIndex(this.registry);
        this.entries = new HashSet<>();

        for (JsonElement element : file.getAsArray(maskKey)) {
            if (!(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString())) {
                TT20.LOGGER.error("(TT20) Mask element '" + element + "' isn't a string");
                return;
            }

            entries.addAll(manageEntry(element.getAsString()));
        }
    }

    @SuppressWarnings("ConstantConditions")
    public List<ResourceLocation> manageEntry(String entry) {
        String[] split = entry.split(":");

        if (split.length != 2) {
            TT20.LOGGER.error("(TT20) '" + entry + "' is not a valid identifier. Correct format is <namespace>:<path>");
            return new ArrayList<>();
        }

        // *:*
        if (split[0].equals("*") && split[1].equals("*")) {
            return index.getIdentifiers();
        }

        // <namespace>:<path>
        if (!split[0].equals("*") && !split[1].equals("*")) {
            return Collections.singletonList(new ResourceLocation(split[0], split[1]));
        }

        // *:<path>
        if (split[0].equals("*")) {
            return index.getPathIndex().getOrDefault(split[1], new ArrayList<>());
        }

        // <namespace>:*
        if (split[1].equals("*")) {
            return index.getNamespaceIndex().getOrDefault(split[0], new ArrayList<>());
        }

        return new ArrayList<>();
    }

    public IForgeRegistry<?> getRegistry() {
        return registry;
    }

    public JSONConfiguration getFile() {
        return file;
    }

    public boolean matches(ResourceLocation identifier) {
        return entries.contains(identifier);
    }

    public boolean isOkay(ResourceLocation identifier) {
        if (maskType == MaskType.WHITELIST) {
            return entries.contains(identifier);
        } else {
            return !entries.contains(identifier);
        }
    }
}
