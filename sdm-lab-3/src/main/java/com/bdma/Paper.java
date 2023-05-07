package com.bdma;

public class Paper {

    String id;
    String paperTitle;
    String area;
    String manager;
    String author;
    String publication;
    String venue;
    String venueType;
    String conferenceType;
    String managerType;
    String conference;
    String journal;
    String reviewer_1;
    String reviewer_2;
    String editor;
    String review_1;
    String review_2;
    String paperType;
    String paperAbstract;
    String decisions_1;
    String decisions_2;
    String year;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
    }

    public String getConferenceType() {
        return conferenceType;
    }

    public void setConferenceType(String conferenceType) {
        this.conferenceType = conferenceType;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReviewer_1() {
        return reviewer_1;
    }

    public void setReviewer_1(String reviewer_1) {
        this.reviewer_1 = reviewer_1;
    }

    public String getReviewer_2() {
        return reviewer_2;
    }

    public void setReviewer_2(String reviewer_2) {
        this.reviewer_2 = reviewer_2;
    }

    public String getReview_1() {
        return review_1;
    }

    public void setReview_1(String review_1) {
        this.review_1 = review_1;
    }

    public String getReview_2() {
        return review_2;
    }

    public void setReview_2(String review_2) {
        this.review_2 = review_2;
    }

    public String getDecisions_1() {
        return decisions_1;
    }

    public void setDecisions_1(String decisions_1) {
        this.decisions_1 = decisions_1;
    }

    public String getDecisions_2() {
        return decisions_2;
    }

    public void setDecisions_2(String decisions_2) {
        this.decisions_2 = decisions_2;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return id + "; " + paperTitle + "; " + area + "; " + manager + "; " + author + "; " + publication + "; " + venue + "; " + venueType + "; " + conferenceType + "; " + managerType + "; " + conference + "; " + journal + "; " + reviewer_1 + "; " + reviewer_2 + "; " + editor + "; " + review_1 + "; " + review_2 + "; " + paperType + "; " + paperAbstract + "; " + decisions_1 + "; " + decisions_2 + "; " + year + "; " + url;

    }
}
