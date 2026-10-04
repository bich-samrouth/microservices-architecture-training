package co.khmer.samrouth.ecommerce.payment.restapi.controller;

import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentCommand;
import co.khmer.samrouth.ecommerce.payment.dto.CancelPaymentResult;
import co.khmer.samrouth.ecommerce.payment.dto.CreatePaymentResult;
import co.khmer.samrouth.ecommerce.payment.restapi.dto.*;
import co.khmer.samrouth.ecommerce.payment.restapi.mapper.PaymentWebMapper;
import co.khmer.samrouth.ecommerce.payment.usecase.*;
import co.khmer.samrouth.restapi.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;
    private final CancelPaymentUseCase cancelPaymentUseCase;
    private final FindPaymentByIdUseCase findPaymentByIdUseCase;
    private final FindPaymentByOrderIdUseCase findPaymentByOrderIdUseCase;
    private final FindAllPaymentsUseCase findAllPaymentsUseCase;
    private final FindCreditEntryByCustomerIdUseCase findCreditEntryByCustomerIdUseCase;
    private final PaymentWebMapper paymentWebMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<PaymentCreateResponse> processPayment(@Valid @RequestBody PaymentCreateRequest request) {
        log.info("Processing payment for order id: {}", request.orderId());
        CreatePaymentResult result = processPaymentUseCase.execute(paymentWebMapper.toCreatePaymentCommand(request));
        return BaseResponse.<PaymentCreateResponse>ok()
                .payload(paymentWebMapper.toPaymentCreateResponse(result));
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaymentCancelResponse> cancelPayment(@Valid @RequestBody PaymentCancelRequest request) {
        CancelPaymentResult result = cancelPaymentUseCase.execute(paymentWebMapper.toCancelPaymentCommand(request));
        return BaseResponse.<PaymentCancelResponse>ok()
                .payload(paymentWebMapper.toPaymentCancelResponse(result));
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaymentCancelResponse> cancelPaymentById(@PathVariable UUID id) {
        log.info("Cancelling payment with id: {}", id);
        CancelPaymentResult result = cancelPaymentUseCase.execute(new CancelPaymentCommand(id));
        return BaseResponse.<PaymentCancelResponse>ok()
                .payload(paymentWebMapper.toPaymentCancelResponse(result));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaymentResponse> getPaymentById(@PathVariable UUID id) {
        log.info("Fetching payment with id: {}", id);
        return BaseResponse.<PaymentResponse>ok()
                .payload(paymentWebMapper.toPaymentResponse(findPaymentByIdUseCase.execute(id)));
    }

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PaymentResponse> getPaymentByOrderId(@PathVariable UUID orderId) {
        log.info("Fetching payment for order id: {}", orderId);
        return BaseResponse.<PaymentResponse>ok()
                .payload(paymentWebMapper.toPaymentResponse(findPaymentByOrderIdUseCase.execute(orderId)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<PaymentResponse>> getAllPayments() {
        log.info("Fetching all payments");
        return BaseResponse.<List<PaymentResponse>>ok()
                .payload(paymentWebMapper.toPaymentResponseList(findAllPaymentsUseCase.execute()));
    }

    @GetMapping("/credit/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<CreditEntryResponse> getCreditEntry(@PathVariable UUID customerId) {
        log.info("Fetching credit entry for customer id: {}", customerId);
        return BaseResponse.<CreditEntryResponse>ok()
                .payload(paymentWebMapper.toCreditEntryResponse(findCreditEntryByCustomerIdUseCase.execute(customerId)));
    }
}
