package com.ericrobertbrewer.projecteuler;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.LongStream;

public class Solution {

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("Usage: <problem-number>");
		}
		final int number = Integer.parseInt(args[0]);
		try {
            final long answer = getAnswer(number);
            System.out.println(answer);
        } catch (IOException e) {
		    e.printStackTrace();
        }
	}

	private static long getAnswer(int problem) throws IOException {
        if (problem == 1) {
            int sum = 0;
            for (int i = 1; i < 1000; i++) {
                if (i % 3 == 0 || i % 5 == 0) {
                    sum += i;
                }
            }
            return sum;

        } else if (problem == 2) {
            final ArrayList<Integer> fib = new ArrayList<>();
            fib.add(0);
            fib.add(1);
            int sum = 0;
            int next = fib.get(fib.size()-2) + fib.get(fib.size()-1);
            while (next < 4000000) {
                if (next % 2 == 0) {
                    sum += next;
                }
                fib.add(next);
                next = fib.get(fib.size()-2) + fib.get(fib.size()-1);
            }
            return sum;

        } else if (problem == 3) {
            final List<Long> primes = new ArrayList<>();
            long n = 600851475143L;
            primes.add(2L);
            ArrayList<Long> factors = new ArrayList<>();
            for (long i = 3; i <= n; i += 2) {
                boolean isPrime = true;
                for (int j = 1; j < primes.size() && isPrime; j++) {
                    if (i % primes.get(j) == 0) {
                        isPrime = false; // not prime
                    }
                }
                if (isPrime) {
                    primes.add(i);
                    while (n % i == 0) {
                        factors.add(i);
                        n /= i;
                    }
                }
            }
            return factors.get(factors.size()-1);

        } else if (problem == 4) {
            int p = -1;
            for (int i = 999; i > 99 && (p == -1 || i * i > p); i--) {
                for (int j = i; j > 99 && (p == -1 || i * j > p); j--) {
                    if (Utility.isPalindrome(i * j)) {
                        p = i * j;
                    }
                }
            }
            return p;

        } else if (problem == 5) {
            final long N = 20;
            // Initialize list of primes up to N, and map of prime factors from 2 -> N
            final List<Long> primes = Prime.getPrimesBelow(N);
            HashMap<Long, Integer> factors = new HashMap<>();
            for (long prime : primes) {
                factors.put(prime, 0);
            }

            // Count prime factors of each number from 2 -> N
            for (long i = N; i >= N/2; i--) {
                long product = i; // Product of some primes; continually divide it (if divisible) by primes found in list of primes
                for (long prime : primes) {
                    int count = 0; // Order of magnitude of this prime as a factor of product
                    while (product > 1 && product % prime == 0) { // if divisible...
                        count++;
                        if (count > factors.get(prime)) {
                            factors.put(prime, count); // Also increment factor count if it exceeds previous count
                        }
                        product /= prime;
                    }
                }
            }

            long result = 1;
            for (long prime : primes) {
                for (int i = 0; i < factors.get(prime); i++) {
                    result *= prime;
                }
            }
            return result;

        } else if (problem == 6) {
            final int n = 100;
            final long sumOfSquares = LongStream.range(1, n + 1)
                    .map(x -> x * x)
                    .sum();
            final long sum = LongStream.range(1, n + 1).sum();
            final long squareOfSum = sum * sum;
            return squareOfSum - sumOfSquares;

        } else if (problem == 7) {
            final int n = 10001;
            final List<Long> primes = Prime.getFirstPrimes(n);
            return primes.get(n - 1);

        } else if (problem == 8) {
            final String num = "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450";
            final long[] digits = num.chars()
                    .map(c -> c - '0')
                    .asLongStream()
                    .toArray();
            final int adjacent = 13;
            long max = -1L;
            for (int i = adjacent; i < digits.length; i++) {
                final long product = Arrays.stream(digits, i - adjacent, i)
                        .reduce(1L, (prev, x) -> x * prev);
                if (product > max) {
                    max = product;
                }
            }
            return max;

        } else if (problem == 9) {
            for (long c = 2; c < 1000L; c++) {
                final long cSquared = c * c;
                final long aEnd = c / 2L;
                for (long a = 1; a < aEnd; a++) {
                    final long aSquared = a * a;
                    final long bSquared = cSquared - aSquared;
                    if (Utility.isSquare(bSquared)) {
                        final long b = (long) Math.sqrt(bSquared);
                        if (a + b + c == 1000L) {
                            return a * b * c;
                        }
                    }
                }
            }
            return -1L;

        } else if (problem == 10) {
            return Prime.getPrimesBelow(2000000).stream().mapToLong(v -> v).sum();

        } else if (problem == 11) {
            // Read grid.
            final String[] GRID = {
                    "08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08",
                    "49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00",
                    "81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65",
                    "52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91",
                    "22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80",
                    "24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50",
                    "32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70",
                    "67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21",
                    "24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72",
                    "21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95",
                    "78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92",
                    "16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57",
                    "86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58",
                    "19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40",
                    "04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66",
                    "88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69",
                    "04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36",
                    "20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16",
                    "20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54",
                    "01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48"
            };
            final long[][] grid = new long[GRID.length][20];
            for (int i = 0; i < GRID.length; i++) {
                final String row = GRID[i];
                final String[] elements = row.split(" ");
                for (int j = 0; j < elements.length; j++) {
                    if (elements[j].startsWith("0")) {
                        grid[i][j] = Long.parseLong(elements[j].substring(1));
                    } else {
                        grid[i][j] = Long.parseLong(elements[j]);
                    }
                }
            }
            // Find greatest product of four adjacent numbers.
            long greatest = 0;
            for (int i = 0; i < grid.length - 3; i++) {
                for (int j = 0; j < grid[i].length - 3; j++) {
                    // Right.
                    final long right = grid[i][j] * grid[i][j + 1] * grid[i][j + 2] * grid[i][j + 3];
                    if (right > greatest) {
                        greatest = right;
                    }
                    // Diagonal down.
                    final long diagonalDown = grid[i][j] * grid[i + 1][j + 1] * grid[i + 2][j + 2] * grid[i + 3][j + 3];
                    if (diagonalDown > greatest) {
                        greatest = diagonalDown;
                    }
                    // Down.
                    final long down = grid[i][j] * grid[i + 1][j] * grid[i + 2][j] * grid[i + 3][j];
                    if (down > greatest) {
                        greatest = down;
                    }
                    // Diagonal up.
                    final long diagonalUp = grid[grid.length - i - 1][j] * grid[grid.length - i - 2][j + 1] *
                            grid[grid.length - i - 3][j + 2] * grid[grid.length - i - 4][j + 3];
                    if (diagonalUp > greatest) {
                        greatest = diagonalUp;
                    }
                }
            }
            return greatest;

        } else if (problem == 12) {
            int i = 1;
            long triangle;
            int factorCount;
            do {
                triangle = Utility.getTriangleNumber(i);
                factorCount = Utility.getFactorCount(triangle);
                i++;
            } while (factorCount < 500);
            return triangle;

        } else if (problem == 13) {
            final String[] numbers = {
                    "37107287533902102798797998220837590246510135740250",
                    "46376937677490009712648124896970078050417018260538",
                    "74324986199524741059474233309513058123726617309629",
                    "91942213363574161572522430563301811072406154908250",
                    "23067588207539346171171980310421047513778063246676",
                    "89261670696623633820136378418383684178734361726757",
                    "28112879812849979408065481931592621691275889832738",
                    "44274228917432520321923589422876796487670272189318",
                    "47451445736001306439091167216856844588711603153276",
                    "70386486105843025439939619828917593665686757934951",
                    "62176457141856560629502157223196586755079324193331",
                    "64906352462741904929101432445813822663347944758178",
                    "92575867718337217661963751590579239728245598838407",
                    "58203565325359399008402633568948830189458628227828",
                    "80181199384826282014278194139940567587151170094390",
                    "35398664372827112653829987240784473053190104293586",
                    "86515506006295864861532075273371959191420517255829",
                    "71693888707715466499115593487603532921714970056938",
                    "54370070576826684624621495650076471787294438377604",
                    "53282654108756828443191190634694037855217779295145",
                    "36123272525000296071075082563815656710885258350721",
                    "45876576172410976447339110607218265236877223636045",
                    "17423706905851860660448207621209813287860733969412",
                    "81142660418086830619328460811191061556940512689692",
                    "51934325451728388641918047049293215058642563049483",
                    "62467221648435076201727918039944693004732956340691",
                    "15732444386908125794514089057706229429197107928209",
                    "55037687525678773091862540744969844508330393682126",
                    "18336384825330154686196124348767681297534375946515",
                    "80386287592878490201521685554828717201219257766954",
                    "78182833757993103614740356856449095527097864797581",
                    "16726320100436897842553539920931837441497806860984",
                    "48403098129077791799088218795327364475675590848030",
                    "87086987551392711854517078544161852424320693150332",
                    "59959406895756536782107074926966537676326235447210",
                    "69793950679652694742597709739166693763042633987085",
                    "41052684708299085211399427365734116182760315001271",
                    "65378607361501080857009149939512557028198746004375",
                    "35829035317434717326932123578154982629742552737307",
                    "94953759765105305946966067683156574377167401875275",
                    "88902802571733229619176668713819931811048770190271",
                    "25267680276078003013678680992525463401061632866526",
                    "36270218540497705585629946580636237993140746255962",
                    "24074486908231174977792365466257246923322810917141",
                    "91430288197103288597806669760892938638285025333403",
                    "34413065578016127815921815005561868836468420090470",
                    "23053081172816430487623791969842487255036638784583",
                    "11487696932154902810424020138335124462181441773470",
                    "63783299490636259666498587618221225225512486764533",
                    "67720186971698544312419572409913959008952310058822",
                    "95548255300263520781532296796249481641953868218774",
                    "76085327132285723110424803456124867697064507995236",
                    "37774242535411291684276865538926205024910326572967",
                    "23701913275725675285653248258265463092207058596522",
                    "29798860272258331913126375147341994889534765745501",
                    "18495701454879288984856827726077713721403798879715",
                    "38298203783031473527721580348144513491373226651381",
                    "34829543829199918180278916522431027392251122869539",
                    "40957953066405232632538044100059654939159879593635",
                    "29746152185502371307642255121183693803580388584903",
                    "41698116222072977186158236678424689157993532961922",
                    "62467957194401269043877107275048102390895523597457",
                    "23189706772547915061505504953922979530901129967519",
                    "86188088225875314529584099251203829009407770775672",
                    "11306739708304724483816533873502340845647058077308",
                    "82959174767140363198008187129011875491310547126581",
                    "97623331044818386269515456334926366572897563400500",
                    "42846280183517070527831839425882145521227251250327",
                    "55121603546981200581762165212827652751691296897789",
                    "32238195734329339946437501907836945765883352399886",
                    "75506164965184775180738168837861091527357929701337",
                    "62177842752192623401942399639168044983993173312731",
                    "32924185707147349566916674687634660915035914677504",
                    "99518671430235219628894890102423325116913619626622",
                    "73267460800591547471830798392868535206946944540724",
                    "76841822524674417161514036427982273348055556214818",
                    "97142617910342598647204516893989422179826088076852",
                    "87783646182799346313767754307809363333018982642090",
                    "10848802521674670883215120185883543223812876952786",
                    "71329612474782464538636993009049310363619763878039",
                    "62184073572399794223406235393808339651327408011116",
                    "66627891981488087797941876876144230030984490851411",
                    "60661826293682836764744779239180335110989069790714",
                    "85786944089552990653640447425576083659976645795096",
                    "66024396409905389607120198219976047599490197230297",
                    "64913982680032973156037120041377903785566085089252",
                    "16730939319872750275468906903707539413042652315011",
                    "94809377245048795150954100921645863754710598436791",
                    "78639167021187492431995700641917969777599028300699",
                    "15368713711936614952811305876380278410754449733078",
                    "40789923115535562561142322423255033685442488917353",
                    "44889911501440648020369068063960672322193204149535",
                    "41503128880339536053299340368006977710650566631954",
                    "81234880673210146739058568557934581403627822703280",
                    "82616570773948327592232845941706525094512325230608",
                    "22918802058777319719839450180888072429661980811197",
                    "77158542502016545090413245809786882778948721859617",
                    "72107838435069186155435662884062257473692284509516",
                    "20849603980134001723930671666823555245252804609722",
                    "53503534226472524250874054075591789781264330331690"
            };
            final BigInteger[] bigInts = new BigInteger[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                bigInts[i] = new BigInteger(numbers[i]);
            }
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger bigInt : bigInts) {
                sum = sum.add(bigInt);
            }
            return Long.parseLong(sum.toString().substring(0, 10));

        } else if (problem == 14) {
            long maxCount = -1L;
            long maxValue = -1L;
            for (long i = 1L; i < 1000000L; i++) {
                long count = 1;
                long x = i;
                while (x != 1L) {
                    if (x % 2 == 0) {
                        x /= 2L;
                    } else {
                        x = 3 * x + 1L;
                    }
                    count++;
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = i;
                }
            }
            return maxValue;

        } else if (problem == 15) {
            // 40! / 20! / 20!
            //
            // 40 * 39 * 38 * 37 * 36 * 35 * 34 * 33 * 32 * 31 * 30 * 29 * 28 * 27 * 26 * 25 * 24 * 23 * 22 * 21 * 20!
            // -------------------------------------------------------------------------------------------------------
            // 20 * 19 * 18 * 17 * 16 * 15 * 14 * 13 * 12 * 11 * 10 *  9 *  8 *  7 *  6 *  5 *  4 *  3 *  2 *  1 * 20!
            //
            // 40 * 39 * 38 * 37 * 36 * 35 * 34 * 33 * 32 * 31 * 30 * 29 * 28 * 27 * 26 * 25 * 24 * 23 * 22 * 21
            // -------------------------------------------------------------------------------------------------
            // 20 * 19 * 18 * 17 * 16 * 15 * 14 * 13 * 12 * 11 * 10 *  9 *  8 *  7 *  6 *  5 *  4 *  3 *  2
            //
            //  2 * 39 *  2 * 37 *  2 * 35 *  2 * 33 *  2 * 31 *  2 * 29 *  2 * 27 *  2 * 25 *  2 * 23 *  2 * 21
            // -------------------------------------------------------------------------------------------------
            //                                                   10 *  9 *  8 *  7 *  6 *  5 *  4 *  3 *  2
            //
            // 39 * 37 * 35 * 33 * 31 * 29 * 27 * 25 * 23 * 21 * 2^10
            // ------------------------------------------------------
            // 10 *  9 *  8 *  7 *  6 *  5 *  4 *  3 *  2
            //
            // 39 * 37 * 35 * 33 * 31 * 29 * 27 * 25 * 23 * 21 * 2^2
            // ------------------------------------------------------
            //  5 *  9      *  7 *  3 *  5      *  3
            //
            // 39 * 37 * 35 * 33 * 31 * 29 * 27 * 25 * 23 * 21 * 2^2
            // ------------------------------------------------------
            // (9 * 3) * (5 * 5) * (7 * 3)
            //
            // 39 * 37 * 35 * 33 * 31 * 29           * 23      * 2^2
            return 39L * 37 * 35 * 33 * 31 * 29 * 23 * 4;

        } else if (problem == 16) {
            BigInteger product = BigInteger.ONE;
            BigInteger two = new BigInteger("2");
            for (int i = 0; i < 1000; i++) {
                product = product.multiply(two);
            }
            final String productString = product.toString();
            long sum = 0L;
            for (int i = 0; i < productString.length(); i++) {
                sum += Long.parseLong(productString.substring(i, i + 1));
            }
            return sum;

        } else if (problem == 17) {
            // Add combined lengths of "one" to "one thousand".
            long sum = 0L;
            for (int i = 1; i <= 1000; i++) {
                final String name = NumberNames.getName(i, false);
                sum += name.length();
            }
            return sum;

        } else if (problem == 18) {
            // Read.
            final String[] TRIANGLE = {
                    "75",
                    "95 64",
                    "17 47 82",
                    "18 35 87 10",
                    "20 04 82 47 65",
                    "19 01 23 75 03 34",
                    "88 02 77 73 07 63 67",
                    "99 65 04 28 06 16 70 92",
                    "41 41 26 56 83 40 80 70 33",
                    "41 48 72 33 47 32 37 16 94 29",
                    "53 71 44 65 25 43 91 52 97 51 14",
                    "70 11 33 28 77 73 17 78 39 68 17 57",
                    "91 71 52 38 17 14 91 43 58 50 27 29 48",
                    "63 66 04 68 89 53 67 30 73 16 69 87 40 31",
                    "04 62 98 27 23 09 70 98 73 93 38 53 60 04 23"
            };
            final long[][] triangle = new long[TRIANGLE.length][TRIANGLE.length];
            for (int i = 0; i < TRIANGLE.length; i++) {
                final String[] elements = TRIANGLE[i].split(" ");
                for (int j = 0; j < elements.length; j++) {
                    if (elements[j].startsWith("0")) {
                        triangle[i][j] = Long.parseLong(elements[j].substring(1));
                    } else {
                        triangle[i][j] = Long.parseLong(elements[j]);
                    }
                }
            }
            // Calculate.
            final long[][] maxes = new long[triangle.length][triangle.length];
            for (int j = 0; j < maxes.length; j++) {
                maxes[maxes.length - 1][j] = triangle[maxes.length - 1][j];
            }
            for (int i = maxes.length - 2; i >= 0; i--) {
                for (int j = 0; j <= i; j++) {
                    maxes[i][j] = triangle[i][j] + Math.max(maxes[i + 1][j], maxes[i + 1][j + 1]);
                }
            }
            return maxes[0][0];

        } else if (problem == 19) {
            int day = 1; // Monday, where Sunday == 0
            int year = 1900;
            final int WEEK_DAYS = 7;
            final int[] MONTH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30 ,31, 30, 31};
            final int[] MONTH_DAYS_LEAP = {31, 29, 31, 30, 31, 30, 31, 31, 30 ,31, 30, 31};
            // Move to 1 Jan, 1901.
            for (int month = 0; month < 12; month++) {
                // The year 1900 is NOT a leap year.
                day = (day + MONTH_DAYS[month]) % WEEK_DAYS;
            }
            year++;
            // Count until 31 Dec, 2000.
            long firstOfMonthSundays = 0L;
            while (year < 2001) {
                for (int month = 0; month < 12; month++) {
                    if (day == 0) {
                        firstOfMonthSundays++;
                    }
                    if (year % 4 == 0) {
                        day = (day + MONTH_DAYS_LEAP[month]) % WEEK_DAYS;
                    } else {
                        day = (day + MONTH_DAYS[month]) % WEEK_DAYS;
                    }
                }
                year++;
            }
            return firstOfMonthSundays;

        } else if (problem == 20) {
            BigInteger product = BigInteger.ONE;
            for (int i = 1; i <= 100; i++) {
                product = product.multiply(new BigInteger(String.valueOf(i)));
            }
            long sum = 0L;
            final String productString = product.toString();
            for (int i = 0; i < productString.length(); i++) {
                sum += Long.parseLong(productString.substring(i, i + 1));
            }
            return sum;

        } else if (problem == 21) {
            // Calculate all d(x) for x <= 10000.
            final Map<Long, Long> d = new HashMap<>();
            for (long x = 2L; x <= 10000L; x++) {
                final Set<Long> factors = Utility.getFactors(x);
                final long properSum = factors.stream().mapToLong(v -> v).sum() - x;
                d.put(x, properSum);
            }
            // Find all amicable pairs.
            final Map<Long, Long> amicable = new HashMap<>();
            for (long key : d.keySet()) {
                final long value = d.get(key);
                if (key != value && d.containsKey(value) && d.get(value) == key) {
                    amicable.put(key, value);
                    amicable.put(value, key);
                }
            }
            // Return sum.
            return amicable.keySet().stream().mapToLong(v -> v).sum();

        } else if (problem == 22) {
            final File namesFile = new File("input", "p022_names.txt");
            final List<String> nameStringLines = Files.readAllLines(namesFile.toPath());
            final List<String> names = new ArrayList<>();
            for (String nameString : nameStringLines) {
                if (nameString.length() == 0) {
                    continue;
                }
                final String[] namesSplit = nameString.split(",");
                for (String name : namesSplit) {
                    names.add(name.replaceAll("\"", ""));
                }
            }
            Collections.sort(names);
            long sum = 0L;
            for (int i = 0; i < names.size(); i++) {
                final String name = names.get(i);
                long value = 0L;
                for (int j = 0; j < name.length(); j++) {
                    final char c = name.charAt(j);
                    value += (long) (c - 'A' + 1);
                }
                sum += (i + 1) * value;
            }
            return sum;

        } else if (problem == 23) {
            final Set<Long> abundants = new HashSet<>();
            for (long x = 12L; x <= 28123L; x++) {
                final Set<Long> factors = Utility.getFactors(x);
                final long properSum = factors.stream().mapToLong(v -> v).sum() - x;
                if (properSum > x) {
                    abundants.add(x);
                }
            }
            final Set<Long> sumsOfTwoAbundant = new HashSet<>();
            for (long abundant : abundants) {
                for (long otherAbundant : abundants) {
                    sumsOfTwoAbundant.add(abundant + otherAbundant);
                }
            }
            long sum = 0L;
            for (long x = 1L; x <= 28123L; x++) {
                if (!sumsOfTwoAbundant.contains(x)) {
                    sum += x;
                }
            }
            return sum;
        } else if (problem == 24) {
            final long target = 1000000L;
            long permutationCount = 10L * 9L * 8L * 7L * 6L * 5L * 4L * 3L * 2L;
            final List<Long> digits = new LinkedList<>();
            for (long i = 0L; i < 10L; i++) {
                digits.add(i);
            }
            long result = 0L;
            long total = 0L;
            for (long i = 10L; i > 0L; i--) {
                final long bucketSize = permutationCount / i;
                final long index = (target - 1 - total) / bucketSize;
                final long digit = digits.get((int) index);
                result = result * 10 + digit;
                digits.remove(digit);
                total += index * bucketSize;
                permutationCount /= i;
            }
            return result;

        } else if (problem == 25) {
            BigInteger nMinus2 = BigInteger.ONE;
            BigInteger nMinus1 = BigInteger.ONE;
            long index = 3L;
            BigInteger n = nMinus2.add(nMinus1);
            while (n.toString().length() < 1000) {
                index++;
                nMinus2 = nMinus1;
                nMinus1 = n;
                n = nMinus2.add(nMinus1);
            }
            return index;

        } else if (problem == 26) {
            long largestD = 0L;
            int largestCycle = 0;
            for (long d = 1L; d < 1000L; d++) {
                final Set<Long> remainders = new HashSet<>();
                int cycle = 0;
                long dividend = 1L;
                while (dividend != 0) {
                    while (dividend < d) {
                        dividend *= 10L;
                        cycle++;
                    }
                    final long remainder = dividend % d;
                    if (remainders.contains(remainder)) {
                        if (cycle > largestCycle) {
                            largestD = d;
                            largestCycle = cycle;
                        }
                        break;
                    }
                    remainders.add(remainder);
                    dividend = remainder;
                }
            }
            return largestD;
        }
        throw new IllegalArgumentException("No solution provided for problem number `" + problem + "`.");
    }
}
