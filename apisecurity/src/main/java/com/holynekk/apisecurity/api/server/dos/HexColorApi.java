package com.holynekk.apisecurity.api.server.dos;

import com.holynekk.apisecurity.api.response.dos.HexColorPaginationResponse;
import com.holynekk.apisecurity.entity.HexColor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/api/dos/v1")
@Validated
public class HexColorApi {

    private static final int COLORS_SIZE = 1000000;

    private List<HexColor> hexColors;

    private String randomColorHex() {
        int randomInt = ThreadLocalRandom.current().nextInt(0xffffff + 1);

        return String.format("#%06x", randomInt);
    }

    public HexColorApi() {
        hexColors = IntStream.rangeClosed(1, COLORS_SIZE).boxed().parallel().map(v-> {
            HexColor hexColor = new HexColor();
            hexColor.setId(v);
            hexColor.setHexColor(randomColorHex());
            return hexColor;
        }).collect(Collectors.toList());
    }

    @GetMapping(value = "/random-colors", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HexColor> randomColors() {
        return hexColors;
    }

    @GetMapping(value = "/random-colors-pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    public HexColorPaginationResponse randomColors(@RequestParam(required = true, name = "page") int page,
                                                   @Valid @Min(10) @Max(100)
                                                   @RequestParam(required = true, name = "size") int size) {
        int startIndex = (page - 1) * size;
        List<HexColor> sublist = hexColors.subList(startIndex, startIndex + size);

        HexColorPaginationResponse response = new HexColorPaginationResponse();
        response.setColors(sublist);
        response.setCurrentPage(page);
        response.setSizes(size);
        response.setTotalPages((int) Math.ceil(COLORS_SIZE/size));
        return response;
    }

}
