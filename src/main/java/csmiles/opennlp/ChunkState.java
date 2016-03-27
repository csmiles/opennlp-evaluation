package csmiles.opennlp;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by craig on 27/03/16.
 */
enum ChunkState {

    PRE_CHUNK_TAG {
        @Override
        ChunkState next(String chunkTag, String token, String nextChunkTag, LinkedList<List<String>> chunks) {
            chunkTag = "B-" + chunkTag;
            if (chunkTag.equalsIgnoreCase(nextChunkTag)) {
                return ChunkState.BEGIN_CHUNK_TAG;
            }

            return ChunkState.PRE_CHUNK_TAG;
        }
    },

    BEGIN_CHUNK_TAG {
        @Override
        ChunkState next(String chunkTag, String token, String nextChunkTag, LinkedList<List<String>> chunks) {
            ArrayList<String> chunk = Lists.newArrayList(token);
            chunks.add(chunk);

            chunkTag = "I-" + chunkTag;
            if (chunkTag.equalsIgnoreCase(nextChunkTag)) {
                return ChunkState.CHUNK_TAG;
            }

            return ChunkState.PRE_CHUNK_TAG;
        }
    },

    CHUNK_TAG {
        @Override
        ChunkState next(String chunkTag, String token, String nextChunkTag, LinkedList<List<String>> chunks) {
            List<String> chunk = chunks.getLast();
            chunk.add(token);

            chunkTag = "I-" + chunkTag;
            if (chunkTag.equalsIgnoreCase(nextChunkTag)) {
                return ChunkState.CHUNK_TAG;
            }

            return ChunkState.PRE_CHUNK_TAG;
        }
    };

    abstract ChunkState next(String chunkTag, String token, String nextChunkTag, LinkedList<List<String>> chunks);

}
