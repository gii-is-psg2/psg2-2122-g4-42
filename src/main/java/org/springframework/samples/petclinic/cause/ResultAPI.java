package org.springframework.samples.petclinic.cause;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultAPI {
    private List<Result> results;
    private List<Integer> image_results;
    private Long total;
    private List<String> answers;
    private Double ts;
    private String device_region;
    private String device_type;
}

@Getter
@Setter
class Result {
    private String title;
    private String link;
    private String description;
    private List<AdditionalLink> additional_links;
    private Cite cite;
}

@Getter
@Setter
class AdditionalLink {
    private String text;
    private String href;
}

@Getter
@Setter
class Cite {
    private String domain;
    private String span;
}