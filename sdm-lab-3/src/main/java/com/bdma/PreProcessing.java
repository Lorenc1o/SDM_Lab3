package com.bdma;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class PreProcessing {

    static String[] paperTypes = {"FullPaper", "ShortPaper", "Poster", "DemoPaper"};
    static String[] areas = {"Machine Learning", "Art", "AI", "Graphs", "Algorithms", "Databases"};
    static String[] conferenceTypes = {"Symposium", "Workshop", "RegularConference"};
    static String[] periodicities = {"Weekly", "Monthly", "Yearly"};

    public static void main(String[] args) {
        Random random = new Random();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("src/main/resources/papers_data.csv"));
            CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(csvReader);

            HashMap<String, Paper> paperHashMap = new HashMap<>();
            HashMap<String, Person> personHashMap = new HashMap<>();
            HashMap<String, Venue> venueHashMap = new HashMap<>();
            HashMap<String, Publications> publicationHashMap = new HashMap<>();
            HashMap<String, String> citiesHashMap = new HashMap<>();

            for (CSVRecord record : parser) {

                String id = record.get("_id");
                String label = record.get("_labels");
                String anAbstract = record.get("abstract");
                String title = record.get("title");
                String name = record.get("name");
                String _start = record.get("_start");
                String _end = record.get("_end");
                String _type = record.get("_type");
                String decision = record.get("decision");
                String review = record.get("review");
                String dob = record.get("date_of_birth");
                String eic = record.get("editors_in_chief");
                String pc = record.get("programme_chairs");
                String publisher = record.get("publisher");
                String url = record.get("url");
                String year = record.get("year");
                String issn = record.get("issn");

                if (!label.equals("")) {
                    switch (label) {
                        case ":Paper":
                            Paper paper = new Paper();
                            paper.setId(id);
                            paper.setPaperAbstract(anAbstract.replaceAll("\n", ", "));
                            paper.setPaperTitle(title.replaceAll("\n", ", ").replaceAll("\"",""));
                            paper.setPaperType(paperTypes[random.nextInt(4)]);
                            paper.setYear(year);
                            paper.setUrl(url);
                            paper.setArea(areas[random.nextInt(5)]);
                            paperHashMap.put(id, paper);
                            break;
                        case ":Person":
                            Person person = new Person();
                            person.setDob(dob);
                            person.setId(id);
                            person.setName(name.replaceAll("\n", ", "));
                            personHashMap.put(id, person);
                            break;
                        case ":Journal": {
                            Venue venue = new Venue();
                            venue.setId(id);
                            venue.setEditor(eic);
                            venue.setName(name);
                            venue.setUrl(url);
                            venue.setVenueType("Journal");
                            venue.setIssn(issn);
                            venue.setPeriodicity(periodicities[random.nextInt(3)]);
                            venueHashMap.put(id, venue);
                            break;
                        }
                        case ":Event": {
                            Venue venue = new Venue();
                            venue.setId(id);
                            venue.setName(name);
                            venue.setUrl(url);
                            venue.setVenueType("Conference");
                            venue.setChair(pc);
                            venue.setPeriodicity(periodicities[random.nextInt(3)]);
                            if (url.equals(""))
                                venue.setConferenceType(conferenceTypes[random.nextInt(3)]);
                            else
                                venue.setConferenceType("ExpertGroup");
                            venueHashMap.put(id, venue);
                            break;
                        }
                        case ":Proceeding":
                        case ":Volume": {
                            Publications publications = new Publications();
                            publications.setId(id);
                            publications.setTitle(title);
                            publications.setChair(pc);
                            publications.setPublisher(publisher);
                            publications.setYear(year);
                            publicationHashMap.put(id, publications);
                            break;
                        }
                        case ":City":
                            citiesHashMap.put(id, name);
                            break;
                    }
                    continue;
                }
                if (_type != null) {
                    switch (_type) {
                        case "authored": {
                            Paper paper = paperHashMap.get(_end);
                            if (paper.getAuthorID() == null)
                                paper.setAuthorID(_start);
                            else
                                paper.setAuthorID(paper.getAuthorID() + "," +_start);
                            paperHashMap.replace(_end, paper);
                            break;
                        }
                        case "correspondingAuthor": {
                            Paper paper = paperHashMap.get(_end);
                            paper.setCorrAuthorID(_start);
                            paperHashMap.replace(_end, paper);
                            break;
                        }
                        case "reviewed": {
                            Paper paper = paperHashMap.get(_end);
                            Person person = personHashMap.get(_start);
                            if (paper.getReviewer_1() == null) {
                                paper.setReviewer_1(person.getName().replaceAll("\n", ", "));
                                paper.setReview_1(review.replaceAll("\n", ", "));
                                paper.setDecisions_1(decision);
                            } else {
                                paper.setReviewer_2(person.getName().replaceAll("\n", ", "));
                                paper.setReview_2(review.replaceAll("\n", ", "));
                                paper.setDecisions_2(decision);
                            }
                            paperHashMap.replace(_end, paper);
                            break;
                        }
                        case "published_in": {
//                            Venue venue = venueHashMap.get(_end);
//                            if (venue != null)
//                                if ( venue.getVenueType().equals("Journal")) {
//                                    paper.setVenue(venue.getName());
//                                    paper.setVenueType("Journal");
//                                    paper.setEditor(venue.getEditor());
//                                    paper.setManagerType("Editor");
//                                } else {
//                                    paper.setVenue(venue.getName());
//                                    paper.setConferenceType(venue.getConferenceType());
//                                    paper.setVenueType("Conference");
//                                    paper.setManager(venue.chair);
//                                    paper.setManagerType("Chair");
//                                }
                            Paper paper = paperHashMap.get(_start);
                            Publications publications = publicationHashMap.get(_end);
                            if (paper.getDecisions_1().equals("Yes") && paper.getDecisions_2().equals("Yes")) {
                                paper.setPublicationId(_end);
                                publications.setArea(paper.getArea());
                            }
                            else
                                paper.setVenueId((String) venueHashMap.keySet().toArray()[random.nextInt(venueHashMap.size())]);
                            publications.setArea(paper.getArea());
                            paperHashMap.replace(_start, paper);
                            publicationHashMap.replace(_end, publications);
                            break;
                        }
                        case "part_of": {
                            Venue venue = venueHashMap.get(_end);
                            venue.setPublication(_start);
                            venueHashMap.replace(_end, venue);
                            break;
                        }
                        case "held_in": {
                            Publications publication = publicationHashMap.get(_start);
                            publication.setCity(citiesHashMap.get(_end));
                            publicationHashMap.replace(_start, publication);
                            break;
                        }
                    }
                }


            }

            String eol = System.getProperty("line.separator");

            Writer papersWriter = new FileWriter("src/main/resources/cleaned_papers.csv");
//            papersWriter.append("id;paperTitle;area;manager;author;publicationID;venue;venueType;conferenceType;managerType;conference;journal;reviewer_1;reviewer_2;editor;review_1;review_2;paperType;paperAbstract;decisions_1;decisions_2;year;url").append(eol);
            papersWriter.append("id;paperTitle;area;authorID;corrAuthorID;publicationID;venueId;reviewer_1;reviewer_2;review_1;review_2;paperType;paperAbstract;decisions_1;decisions_2;year;url").append(eol);
            for (Map.Entry<String, Paper> entry : paperHashMap.entrySet())
                papersWriter.append(entry.getValue().toString()).append(eol);
            papersWriter.close();

            Writer personsWriter = new FileWriter("src/main/resources/cleaned_persons.csv");
            personsWriter.append("id;name;dob").append(eol);
            for (Map.Entry<String, Person> entry : personHashMap.entrySet())
                personsWriter.append(entry.getValue().toString()).append(eol);
            personsWriter.close();

            Writer venuesWriter = new FileWriter("src/main/resources/cleaned_venues.csv");
            venuesWriter.append("id;area;publication;venueType;conferenceType;editor;url;name;chair;issn;periodicity").append(eol);
            for (Map.Entry<String, Venue> entry : venueHashMap.entrySet())
                venuesWriter.append(entry.getValue().toString()).append(eol);
            venuesWriter.close();

            Writer publicationsWriter = new FileWriter("src/main/resources/cleaned_publications.csv");
            publicationsWriter.append("id;title;year;publisher;chair;city;area").append(eol);
            for (Map.Entry<String, Publications> entry : publicationHashMap.entrySet())
                publicationsWriter.append(entry.getValue().toString()).append(eol);
            publicationsWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
