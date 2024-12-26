## Documentación 📜
**Metódo createOrderWithCart**
![Descripción de la imagen](images/mi-imagen.png)
Este método se encarga de realizar una compra utilizando los datos de una película, un horario y asientos seleccionados. El proceso incluye validaciones, la actualización del carrito de compras, el cálculo del precio total y la creación de una nueva orden. En caso de un error durante el proceso, se revierten el estado del asiento y vuelve a estar disponible.

Parámetros
movieIds (List<Long>): Lista de identificadores de las películas seleccionadas para la orden.
scheduleIds (List<Long>): Lista de identificadores de los horarios asociados a las películas seleccionadas.
seatIds (List<Long>): Lista de identificadores de los asientos seleccionados.
orderDTO (OrderDTO): Es importante dejarlo VACÍO. Se crea a partir de los valores en los parámetros anteriores.
**Flujo de trabajo**
Obtener los detalles de las entidades:

Através de los IDs que se pasan por parámetro, se consulta a su correspondiente microservicio para obtener el objeto deseado, o por ejemplo también modificar el estado del asiento al que le pertenece el ID (true-disponible / false-no disponible).	
Calcular el precio total:

El precio total de la orden se calcula sumando el precio de cada asiento seleccionado.
**Validación:**

Se valida que todos los datos estén completos y sean coherentes mediante el método validate. Si alguna de las validaciones falla, se lanza una excepción ValidationException.
Las validaciones incluyen comprobar si:
Todos los datos necesarios están presentes.
El horario esté relacionado con la película correcta.
Los asientos pertenezcan al teatro correspondiente.
**Actualizar el carrito y crear la orden:**

Se llama al servicio purchaseService para agregar los asientos seleccionados al carrito de compras y se guarda la información de la compra.
Luego, se crea un pedido mediante el orderService.
**Rollback en caso de error:**

Si ocurre un error durante el proceso (por ejemplo, al agregar al carrito o al guardar la compra), se realiza un rollback, restaurando el estado de los asientos al estado original.