package ro.msg.learning.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ro.msg.learning.shop.exception.LocationNotFoundException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixResponse {

    @JsonProperty("rows")
    private List<Row> rows = Collections.emptyList();

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Row {

        @JsonProperty("elements")
        private List<Element> elements = Collections.emptyList();

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Element {

            @JsonProperty("distance")
            private Distance distance;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            private static class Distance {

                @JsonProperty("value")
                private Long value;
            }
        }
    }

    public int getNearestLocationIndex() {
        Row.Element el = getRows().get(0).getElements()
                .stream()
                .min(Comparator.comparing(element -> element.getDistance().getValue()))
                .orElseThrow(LocationNotFoundException::new);

        return getRows().get(0).getElements().indexOf(el);
    }

}
