/** Distance interface.
 *  Authors, JHED
 *  Nicholas Garcia, ngarcia5
 *  Ashwin Bhat, abhat4
 *  Lousanna Cai, lcai10
 *  Shain Bannowsky, sbannow1
 *  Data Structures 600.226.02
 *  Project 4
 * @param <T>
 */
interface Distance<T> {
    /**
     * Distance method.
     * @return double
     * @param one pixel
     * @param two pixel
     */
    double distance(T one, T two);
}