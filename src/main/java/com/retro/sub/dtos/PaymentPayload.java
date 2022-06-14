package com.retro.sub.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentPayload implements Serializable {

    private Beneficiary beneficiary;
    private CreditTransferTransaction creditTransferTransaction;
    private Integer numberOfTransactions;
    private String paymentInformationId;
    private PaymentTypeInformation paymentTypeInformation;



    public static PaymentPayload fromSubmitDto(SubmitDto submitDto){
       return PaymentPayload.builder()
                .beneficiary(new Beneficiary(new Creditor(submitDto.getCreditorName()), new CreditorAccount(submitDto.getCreditorIban())))
                .creditTransferTransaction(new CreditTransferTransaction(new InstructedAmount(submitDto.getAmount(), submitDto.getCurrency()),
                        null, null))
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PaymentTypeInformation {
        private String categoryPurpose;
        private String localInstrument;
        private String serviceLevel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CreditTransferTransaction {
        private InstructedAmount instructedAmount;
        private RemittanceInformation remittanceInformation;
        private String requestedExecutionDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class InstructedAmount {
        private String amount;
        private String currency;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RemittanceInformation {
        private String unstructured;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CreditorAccount {
        private String iban;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Beneficiary {
        private Creditor creditor;
        private CreditorAccount creditorAccount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Creditor {
        private String name;

    }
}
