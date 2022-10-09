package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.Payment;
import pl.lets_eat_together.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(@Qualifier("paymentRepository") PaymentRepository paymentRepository
                        ) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id){
        Optional<Payment> found = paymentRepository.findById(id);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public Payment addPayment(Payment newPayment){
        return paymentRepository.saveAndFlush(newPayment);
    }


    public String deletePayment(Long id){
         Payment payment = getPaymentById(id);
         paymentRepository.delete(payment);
         return "Payment with id=" + id + " deleted";
    }

}
