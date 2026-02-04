const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
let currentPage = 0;

function fetchBasedOnEndPoints( url,method){

    fetch(url, {
        method: method,// Specify the HTTP method
        credentials: "include"

    }).then(response => response.json())
        .then(orders => {
            const tbody = document.getElementById('books-list');
            tbody.innerHTML = ''; // clear existing rows if any
            orders.forEach(order => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${order.bookTitle}</td>
                    <td>${order.bookAuthor}</td>

                    <td>${order.pricePerBook}</td>
                    <td>${order.quantity}</td>
                    <td>${order.total}</td>
                    <td>${order.transactionDate}</td>`;

                tbody.appendChild(row);

            });
        })
        .catch(error => {
            console.error('Error loading books:', error);
        });
}
function  fetchAllBooks(page=0) {
    currentPage =page;
    const container = document.getElementById("books-container");
    const userId = container.dataset.userId;
    fetchBasedOnEndPoints(`/api/order_history/${userId}?page=${page}`,"GET");

}
fetchAllBooks();
document.getElementById("nextBtn").addEventListener("click", () => {
    fetchAllBooks(currentPage + 1);
});
document.getElementById("prevBtn").addEventListener("click", () => {
    if(currentPage>0)
        fetchAllBooks(currentPage - 1);
});


