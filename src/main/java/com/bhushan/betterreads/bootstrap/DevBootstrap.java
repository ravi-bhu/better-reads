package com.bhushan.betterreads.bootstrap;

import com.bhushan.betterreads.author.Author;
import com.bhushan.betterreads.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class DevBootstrap implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) {
        loadAuthorsInDb();
    }

    private void loadAuthorsInDb() {
        var path = Path.of("E:\\Downloads\\Compressed\\ol_dump_authors_2021-11-30.txt");
        try (var lines = Files.lines(path)) {
            System.out.println(lines.parallel().count());
//                    .map(this::toAuthorJson)
//                    .filter(Objects::nonNull)
//                    .map(this::toAuthor)
//                    .peek(System.out::println)
//                    .forEachOrdered(authorRepository::save);
        } catch (IOException e) {
            log.error("Error Reading authors: {}", e.getMessage());
        }
    }

    private JSONObject toAuthorJson(String authorRawData) {
        String authorJson = authorRawData.substring(authorRawData.indexOf("{"));
        try {
            return new JSONObject(authorJson);
        } catch (JSONException e) {
            log.error("Error parsing author json: {}", e.getMessage());
            return null;
        }
    }

    private Author toAuthor(JSONObject authorJson) {
        Author author = new Author();
        String id = authorJson.optString("key").replace("/authors/", "").replace("/a/", "");
        author.setId(id);
        author.setName(authorJson.optString("name"));
        author.setPersonalName(authorJson.optString("personal_name"));
        return author;
    }
}
