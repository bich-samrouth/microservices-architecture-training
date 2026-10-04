package co.khmer.samrouth.ecommerce.payment.restapi.mapper;

import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentResult;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentResult;
import co.khmer.samrouth.ecommerce.payment.dto.CreditEntryDto;
import co.khmer.samrouth.ecommerce.payment.dto.PaymentResponseDto;
import co.khmer.samrouth.ecommerce.payment.restapi.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentWebMapper {

    public CreatePaymentCommand toCreatePaymentCommand(PaymentCreateRequest request) {
        return CreatePaymentCommand.builder()
                .orderId(request.orderId())
                .customerId(request.customerId())
                .price(request.price())
                .build();
    }

    public PaymentCreateResponse toPaymentCreateResponse(CreatePaymentResult result) {
        return PaymentCreateResponse.builder()
                .paymentId(result.paymentId())
                .orderId(result.orderId())
                .customerId(result.customerId())
                .price(result.price())
                .paymentStatus(result.paymentStatus())
                .createdAt(result.createdAt())
                .failureMessages(result.failureMessages())
                .build();
    }

    public CancelPaymentCommand toCancelPaymentCommand(PaymentCancelRequest request) {
        return CancelPaymentCommand.builder()
                .paymentId(request.paymentId())
                .build();
    }

    public PaymentCancelResponse toPaymentCancelResponse(CancelPaymentResult result) {
        return PaymentCancelResponse.builder()
                .paymentId(result.paymentId())
                .paymentStatus(result.paymentStatus())
                .failureMessages(result.failureMessages())
                .build();
    }

    public PaymentResponse toPaymentResponse(PaymentResponseDto dto) {
        return PaymentResponse.builder()
                .id(dto.id())
                .orderId(dto.orderId())
                .customerId(dto.customerId())
                .price(dto.price())
                .paymentStatus(dto.paymentStatus())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }

    public List<PaymentResponse> toPaymentResponseList(List<PaymentResponseDto> dtos) {
        return dtos.stream().map(this::toPaymentResponse).toList();
    }

    public CreditEntryResponse toCreditEntryResponse(CreditEntryDto dto) {
        return CreditEntryResponse.builder()
                .id(dto.id())
                .customerId(dto.customerId())
                .totalCreditAmount(dto.totalCreditAmount())
                .build();
    }
}
