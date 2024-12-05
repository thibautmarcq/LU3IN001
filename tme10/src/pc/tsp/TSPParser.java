package pc.tsp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Parses TSPLIB .tsp files and constructs a CityMap.
 */
public class TSPParser {
    /**
     * Parses a TSPLIB file and returns a CityMap object.
     *
     * @param filePath Path to the .tsp file.
     * @return CityMap representing the cities and their coordinates.
     * @throws IOException If an I/O error occurs.
     * @throws UnsupportedOperationException If EDGE_WEIGHT_TYPE is not EUC_2D.
     */
    public static CityMap parse(String filePath) throws IOException {
        ArrayList<City> cities = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean nodeSection = false;
        boolean euc2d = false;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            // Check for EDGE_WEIGHT_TYPE
            if (line.startsWith("EDGE_WEIGHT_TYPE")) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String weightType = parts[1].trim();
                    if (!weightType.equalsIgnoreCase("EUC_2D")) {
                        reader.close();
                        throw new UnsupportedOperationException("Unsupported EDGE_WEIGHT_TYPE: " + weightType);
                    } else {
                        euc2d = true;
                    }
                }
                continue;
            }

            if (line.equals("NODE_COORD_SECTION")) {
                nodeSection = true;
                continue;
            }
            if (line.equals("EOF")) {
                break;
            }
            if (nodeSection) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    int index = Integer.parseInt(parts[0]) - 1; // TSPLIB indices start at 1
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    City city = new City(index, x, y);
                    cities.add(city);
                }
            }
        }
        reader.close();

        if (!euc2d) {
            throw new UnsupportedOperationException("EDGE_WEIGHT_TYPE not specified as EUC_2D.");
        }

        City[] cityArray = cities.toArray(new City[0]);
        // Build the CityMap, which computes minX, minY, maxX, maxY, and initializes DistanceMatrix
        return new CityMap(cityArray);
    }
    
    /**
     * Parses the optimal tour file corresponding to the given .tsp file.
     *
     * @param tspFilePath Path to the .tsp file.
     * @return Tour object representing the optimal tour, or null if the file is not found.
     * @throws IOException If an I/O error occurs during parsing.
     */
    public static Tour findAndParseOptimal(String tspFilePath) throws IOException {
      // Construct the optimal tour file path by replacing the .tsp extension with .opt.tour
      if (!tspFilePath.toLowerCase().endsWith(".tsp")) {
          throw new IllegalArgumentException("The provided file does not have a .tsp extension.");
      }

      String baseName = tspFilePath.substring(0, tspFilePath.length() - 4); // Remove ".tsp"
      String optTourPath = baseName + ".opt.tour";

      File optTourFile = new File(optTourPath);
      if (!optTourFile.exists()) {
          // Optimal tour file does not exist
          return null;
      }
      return parseTour(optTourPath);
    }
    
    /**
     * Parses the optimal tour file corresponding to the given .tsp file.
     *
     * @param tspFilePath Path to the .tsp file.
     * @return Tour object representing the optimal tour, or null if the file is not found.
     * @throws IOException If an I/O error occurs during parsing.
     */
    public static Tour parseTour(String tspFilePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(tspFilePath));
        String line;
        boolean tourSection = false;
        ArrayList<Integer> tourIndices = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            // Skip header information
            if (line.equals("TOUR_SECTION")) {
                tourSection = true;
                continue;
            }
            if (line.equals("EOF")) {
                break;
            }

            if (tourSection) {
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                if (line.equals("-1")) {
                    break; // End of tour section
                }
                try {
                    int cityIndex = Integer.parseInt(line) - 1; // Convert to 0-based index
                    tourIndices.add(cityIndex);
                } catch (NumberFormatException e) {
                    // Invalid line format, skip or handle as needed
                    System.err.println("Invalid city index in optimal tour file: " + line);
                }
            }
        }
        reader.close();

        if (tourIndices.isEmpty()) {
            // No valid tour found
            return null;
        }

        // Convert the list to an array
        int[] cityIndices = new int[tourIndices.size()];
        for (int i = 0; i < tourIndices.size(); i++) {
            cityIndices[i] = tourIndices.get(i);
        }

        // Create and return the Tour object
        return new Tour(cityIndices);
    }
    
    /**
     * Saves a Tour to a .tour file with a timestamp and fitness in the filename.
     *
     * @param tour     The Tour to save.
     * @param file     The original .tsp file path.
     * @param fitness  The fitness (length) of the tour.
     * @throws IOException If an I/O error occurs.
     */
    public static void saveTour(Tour tour, String file, double fitness) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File originalFile = new File(file);
        String baseName = originalFile.getName().substring(0, originalFile.getName().lastIndexOf('.'));
        String directory = originalFile.getParent();
        if (directory == null) {
            directory = ".";
        }
        String outputFileName = String.format("%s.%s.%f.tour", baseName, timestamp, fitness);
        File outputFile = new File(directory, outputFileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("NAME : " + outputFileName + "\n");
        writer.write("TYPE : TOUR\n");
        writer.write("COMMENT : Tour length: " + fitness + "\n");
        writer.write("DIMENSION : " + tour.getCityIndices().length + "\n");
        writer.write("TOUR_SECTION\n");
        for (int cityIndex : tour.getCityIndices()) {
            writer.write((cityIndex + 1) + "\n");
        }
        writer.write("EOF\n");
        writer.close();
    }
}

