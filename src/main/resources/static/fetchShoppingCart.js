const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function fetchBasedOnEndPoints( url,method){

    fetch(url, {
        method: method,// Specify the HTTP method
        credentials: "include"

    }).then(response => response.json())
        .then(books => {
            const tbody = document.getElementById('books-list');
            tbody.innerHTML = ''; // clear existing rows if any
            books.forEach(book => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${book.bookTitle}</td>
                    <td>${book.bookAuthor}</td>
                    <td>${book.price}</td>
                    <td>${book.category}</td>
                    <td>${book.quantity}</td>`;

                tbody.appendChild(row);

            });
        })
        .catch(error => {
            console.error('Error loading books:', error);
        });
}
function  fetchAllBooks() {

    const container = document.getElementById("books-container");
    const userId = container.dataset.userId;
    console.log(userId);
    fetchBasedOnEndPoints(`/api/shopping_cart/${userId}`,"GET");

}
fetchAllBooks();


