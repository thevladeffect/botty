import jokes.Jokes;
import permutation.Permutation;
import spark.Route;
import weather.Weather;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.*;


public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        String port = System.getenv("PORT");
        port(port == null ? 9999 : Integer.parseInt(port));

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        Jokes.createJokes();
        log.info("Jokes created.");

        get("/chat", chatRoute);
    }

    private static String weatherString = "What is the weather today in: (\\w+), (\\w+)";
    private static Pattern weatherPattern = Pattern.compile(weatherString);

    private static String jokeString = "Tell me joke #(\\d+)";
    private static Pattern jokePattern = Pattern.compile(jokeString);

    private static String permutationString = "Let me know if these are permutations of each other: (\\w+), (\\w+)";
    private static Pattern permutationPattern = Pattern.compile(permutationString);

    private static Route chatRoute = (request, response) -> {
        String message = request.queryParams("message");
        String uid = request.queryParams("uid");

        log.info("Received: " + message);

        if (message.matches(weatherString)) {
            Matcher matcher = weatherPattern.matcher(message);

            if (matcher.find()) {
                String city = matcher.group(1);
                String country = matcher.group(2);

                String reply = "The temperature in " + city + ", " + country + " is " + Weather.getWeather(city, country);
                log.info("Responded: " + reply);
                return reply;
            }
        } else if (message.matches(jokeString)) {
            Matcher matcher = jokePattern.matcher(message);

            if (matcher.find()) {
                int jokeNo = Integer.parseInt(matcher.group(1));

                String reply = Jokes.getJoke(jokeNo, uid);
                log.info("Responded: " + reply);
                return reply;
            }
        } else if (message.matches(permutationString)) {
            Matcher matcher = permutationPattern.matcher(message);

            if (matcher.find()) {
                String first = matcher.group(1);
                String second = matcher.group(2);

                String reply = Permutation.check(first, second) ? "Yes, they are." : "No, they are not.";
                log.info("Responded: " + reply);
                return reply;
            }
        }
        String reply = "I'm sorry, I'm not sure what you mean";
        log.info("Responded: " + reply);
        return reply;
    };
}
