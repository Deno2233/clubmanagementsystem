package org.example.barmangementsystem.services;

import org.example.barmangementsystem.dto.CategoryProfitDTO;
import org.example.barmangementsystem.dto.ProductProfitDTO;
import org.example.barmangementsystem.dto.ProfitTrendDTO;
import org.example.barmangementsystem.dto.SessionProfitDTO;
import org.example.barmangementsystem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitService {
    private final OrderItemRepository orderItemRepository;

    public ProfitService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<ProductProfitDTO> getProductProfits() {
        return orderItemRepository.getProfitPerProduct();
    }

    public List<CategoryProfitDTO> getCategoryProfits() {
        return orderItemRepository.getProfitPerCategory();
    }

    public List<SessionProfitDTO> getSessionProfits() {
        return orderItemRepository.getProfitPerSession();
    }

    public List<ProfitTrendDTO> getDailyProfitTrend() {
        return orderItemRepository.getDailyProfitTrend();
    }

    public List<ProfitTrendDTO> getMonthlyProfitTrend() {
        return orderItemRepository.getMonthlyProfitTrend();
    }

    public List<ProfitTrendDTO> getWeeklyProfitTrend() {
        return orderItemRepository.getWeeklyProfitTrend();
    }
}
