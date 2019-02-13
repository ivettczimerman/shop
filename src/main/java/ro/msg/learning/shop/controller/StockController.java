package ro.msg.learning.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.service.StockService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(path = "/stocks/{location_id}", produces = "text/csv")
    public List<Stock> getStockByLocationId(@PathVariable int locationId, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; file=stocks.csv");
        return stockService.getStockForLocation(locationId);
    }
}
