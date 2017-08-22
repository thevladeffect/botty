package jokes;

import redis.clients.jedis.Jedis;

public class Jokes {

    private static String redisUrl = System.getenv("REDIS_URL");
    private static Jedis jedis = new Jedis(redisUrl == null ? "localhost" : redisUrl);

    public static String getJoke(int no, String uid) {

        if (uid == null) {
            return "I'm sorry, I don't know who you are. I only tell jokes to people I know.";
        }

        String jokeKey = "joke" + no;

        if (jedis.exists(jokeKey)) {
            if (jedis.exists(uid)) {
                if(jedis.get(uid).contains(jokeKey + "~")) {
                    return "You already know this joke. Try to remember it.";
                } else {
                    jedis.append(uid, jokeKey + "~");
                    return jedis.get(jokeKey);
                }
            } else {
                jedis.set(uid, jokeKey + "~");
                return jedis.get(jokeKey);
            }
        } else {
            return "I don't know this joke.";
        }
    }

    public static void createJokes() {
        for(int i = 1; i <= 50; i++) {
            jedis.set("joke" + i, "This is joke number " + i + ".");
        }
    }
}
