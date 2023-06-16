import br.com.pradofigu.maestro.domain.customer.model.Order
import br.com.pradofigu.maestro.domain.customer.ports.input.OrderInputPort
import br.com.pradofigu.maestro.domain.customer.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.domain.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderUseCase(private val orderDataAccessPort: OrderDataAccessPort) : OrderInputPort {

    override suspend fun findAll() = orderDataAccessPort.findAll()
    override suspend fun findById(id: UUID) = orderDataAccessPort.findById(id)
            ?: throw ResourceNotFoundException("Order not found")
    override suspend fun findByOrderNumber(orderNumber: Number) = orderDataAccessPort.findByOrderNumber(orderNumber)
            ?: throw ResourceNotFoundException("Order not found")
    override suspend fun create(order: Order) = orderDataAccessPort.create(order)
            ?: throw IllegalStateException("Unable to create order")
    override suspend fun updateStatus(order: Order) = orderDataAccessPort.updateStatus(order)
            ?: throw IllegalStateException("Unable to update order")
}