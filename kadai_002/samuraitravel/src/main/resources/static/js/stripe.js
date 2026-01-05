const stripe = Stripe('pk_test_51SY7pOHiN8soUDyl4KWr5zm4CjkGISX15vx8f2tsccjegpFbhvzrwhVI62BLNB4LHtMppC58E7ukmlLJm6BLnDx900B8nwGiPC');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
 stripe.redirectToCheckout({
   sessionId: sessionId
 })
});