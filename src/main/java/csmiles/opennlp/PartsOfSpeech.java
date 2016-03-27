package csmiles.opennlp;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Predicate;

/**
 * This class is used for extracting the parts of speech from a document.
 *
 * @author Craig Smiles
 */
@SuppressWarnings("WeakerAccess")
public class PartsOfSpeech {

    private final List<Chunk> chunks;

    public PartsOfSpeech(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public static boolean isNoun(String tag) {
        return tag.startsWith("NN");
    }

    public static boolean isCommonNoun(String tag) {
        return tag.equals("NN") || tag.equals("NNS");
    }

    public static boolean isProperNoun(String tag) {
        return tag.equals("NNP") || tag.equals("NNPS");
    }

    public List<String> getPos(Predicate<String> predicate) {
        List<String> result = Lists.newArrayList();
        for (Chunk chunk : chunks) {
            for (int i = 0; i < chunk.tokens.length; i++) {
                if (predicate.test(chunk.posTags[i])) {
                    result.add(chunk.tokens[i]);
                }
            }
        }

        return result;
    }

}
