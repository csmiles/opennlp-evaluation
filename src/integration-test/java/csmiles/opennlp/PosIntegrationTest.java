package csmiles.opennlp;

import com.google.common.collect.Lists;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerModel;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Integration test demonstrating how parts of speech can be extracted from a document.
 *
 * @author Craig Smiles
 */
public class PosIntegrationTest {

    private Chunker chunker;

    @Before
    public void setup() throws Exception {
        SentenceModel sentenceModel = new SentenceModel(res("/csmiles/opennlp/models/en-sent.bin"));
        TokenizerModel tokenModel = new TokenizerModel(res("/csmiles/opennlp/models/en-token.bin"));
        POSModel posModel = new POSModel(res("/csmiles/opennlp/models/en-pos-perceptron.bin"));
        ChunkerModel chunkerModel = new ChunkerModel(res("/csmiles/opennlp/models/en-chunker.bin"));
        chunker = new Chunker(sentenceModel, tokenModel, posModel, chunkerModel);
    }

    private InputStream res(String res) {
        return PosIntegrationTest.class.getResourceAsStream(res);
    }

    @Test
    public void getPos_JackAndJill_AllNouns() throws Exception {
        String doc = "Jack and Jill went up the hill to fetch a pail of water.";
        List<Chunk> chunks = chunker.chunk(doc);
        PartsOfSpeech pos = new PartsOfSpeech(chunks);
        List<String> nouns = pos.getPos(PartsOfSpeech::isNoun);

        ArrayList<String> expected = Lists.newArrayList("Jack", "Jill", "hill", "pail", "water");
        assertEquals(expected, nouns);
    }

    @Test
    public void getPos_JackAndJill_CommonNouns() throws Exception {
        String doc = "Jack and Jill went up the hill to fetch a pail of water.";
        List<Chunk> chunks = chunker.chunk(doc);
        PartsOfSpeech pos = new PartsOfSpeech(chunks);
        List<String> nouns = pos.getPos(PartsOfSpeech::isCommonNoun);

        ArrayList<String> expected = Lists.newArrayList("hill", "pail", "water");
        assertEquals(expected, nouns);
    }

    @Test
    public void getPos_JackAndJill_ProperNouns() throws Exception {
        String doc = "Jack and Jill went up the hill to fetch a pail of water.";
        List<Chunk> chunks = chunker.chunk(doc);
        PartsOfSpeech pos = new PartsOfSpeech(chunks);
        List<String> nouns = pos.getPos(PartsOfSpeech::isProperNoun);

        ArrayList<String> expected = Lists.newArrayList("Jack", "Jill");
        assertEquals(expected, nouns);
    }

    @Test
    public void getChunk_JackAndJill_NounPhrase() throws Exception {
        String doc = "Jack and Jill went up the hill to fetch a pail of water.";
        List<Chunk> chunks = chunker.chunk(doc);
        PartsOfSpeech pos = new PartsOfSpeech(chunks);
        List<List<String>> phrases = pos.getChunk("NP");

        ArrayList<String> expected;
        expected = Lists.newArrayList("Jack", "and", "Jill");
        assertEquals(expected, phrases.get(0));
        expected = Lists.newArrayList("the", "hill");
        assertEquals(expected, phrases.get(1));
        expected = Lists.newArrayList("a", "pail");
        assertEquals(expected, phrases.get(2));
        expected = Lists.newArrayList("water");
        assertEquals(expected, phrases.get(3));
    }

}