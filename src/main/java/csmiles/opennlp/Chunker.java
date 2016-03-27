package csmiles.opennlp;

import com.google.common.collect.Lists;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.util.List;

/**
 * A convenience class that uses Apache OpenNLP to tokenize a document. The result is a list of {@link Chunk}s.
 * Each {@link Chunk} includes the tokens, POS tags and chunk tags for a sentence.
 *
 * @author Craig Smiles
 */
@SuppressWarnings("WeakerAccess")
public class Chunker {

    private final SentenceModel sentenceModel;

    private final TokenizerModel tokenizerModel;

    private final POSModel posModel;

    public Chunker(SentenceModel sentenceModel, TokenizerModel tokenizerModel, POSModel posModel) {
        this.sentenceModel = sentenceModel;
        this.tokenizerModel = tokenizerModel;
        this.posModel = posModel;
    }

    public List<Chunk> chunk(String doc) {
        SentenceDetector sentenceDetector = new SentenceDetectorME(sentenceModel);
        TokenizerME tokenizer = new TokenizerME(tokenizerModel);
        POSTaggerME tagger = new POSTaggerME(posModel);

        List<Chunk> chunks = Lists.newArrayList();
        String[] sentences = sentenceDetector.sentDetect(doc);
        for (String sentence : sentences) {
            String[] tokens = tokenizer.tokenize(sentence);
            String[] tags = tagger.tag(tokens);

            Chunk chunk = new Chunk(tokens, tags);
            chunks.add(chunk);
        }

        return chunks;
    }

}
