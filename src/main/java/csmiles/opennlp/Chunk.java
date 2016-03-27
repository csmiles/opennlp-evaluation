package csmiles.opennlp;

/**
 * A class for storing the tokens, POS (part of speech) tags and chunk tags.
 * <p>
 * The POS tags can be found <a href="http://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html">here</a>
 *
 * @author Craig Smiles
 */
@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class Chunk {

    public String[] tokens;
    public String[] posTags;
    public String[] chunkTags;

    public Chunk(String[] tokens, String[] posTags, String[] chunkTags) {
        this.tokens = tokens;
        this.posTags = posTags;
        this.chunkTags = chunkTags;
    }

}
