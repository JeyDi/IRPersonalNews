package app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Measures the Cosine similarity of two vectors of an inner product space and
 * compares the angle between them.
 * <p>
 * <p>
 * For further explanation about the Cosine Similarity, refer to
 * http://en.wikipedia.org/wiki/Cosine_similarity.
 * </p>
 *
 * @since 1.0
 */
public class CosineSimilarity {

    /**
     * Calculates the cosine similarity for two given vectors.
     *
     * @param leftVector  left vector
     * @param rightVector right vector
     * @return cosine similarity between the two vectors
     */
    public Double cosineSimilarity(final Map<String, Float> leftVector,
                                   final Map<String, Float> rightVector) {
        if (leftVector == null || rightVector == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }

        final Set<String> keys = getIntersection(leftVector, rightVector);

        final double dotProduct = dot(leftVector, rightVector, keys);
        double d1 = 0.0d;
        for (final Float value : leftVector.values()) {
            d1 += Math.pow(value, 2);
        }
        double d2 = 0.0d;
        for (final Float value : rightVector.values()) {
            d2 += Math.pow(value, 2);
        }
        double cosineSimilarity;
        if (d1 <= 0.0 || d2 <= 0.0) {
            cosineSimilarity = 0.0;
        } else {
            cosineSimilarity = dotProduct / (Math.sqrt(d1) * Math.sqrt(d2));
        }
        return cosineSimilarity;
    }

    /**
     * Returns a set with strings common to the two given maps.
     *
     * @param leftVector  left vector map
     * @param rightVector right vector map
     * @return common strings
     */
    private Set<String> getIntersection(final Map<String, Float> leftVector,
                                        final Map<String, Float> rightVector) {
        final Set<String> intersection = new HashSet<>(leftVector.keySet());
        intersection.retainAll(rightVector.keySet());
        return intersection;
    }

    /**
     * Returns a set with strings common to the two given maps.
     *
     * @param leftVector  left vector map
     * @param rightVector right vector map
     * @return combined strings
     */
    private Set<String> getUnion(final Map<String, Float> leftVector,
                                 final Map<String, Float> rightVector) {
        final Set<String> union = new HashSet<>(leftVector.keySet());
        union.addAll(rightVector.keySet());
        return union;
    }


    /**
     * Computes the dot product of two vectors. It ignores remaining elements. It means
     * that if a vector is longer than other, then a smaller part of it will be used to compute
     * the dot product.
     *
     * @param leftVector   left vector
     * @param rightVector  right vector
     * @param union common elements
     * @return the dot product
     */
    private double dot(final Map<String, Float> leftVector, final Map<String, Float> rightVector,
                       final Set<String> union) {
        long dotProduct = 0;
        for (final CharSequence key : union) {
            dotProduct += leftVector.getOrDefault(key, 0f) * rightVector.getOrDefault(key, 0f);
        }
        return dotProduct;
    }

}