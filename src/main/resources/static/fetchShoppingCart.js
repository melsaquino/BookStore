const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
function fetchBasedOnEndPoints( url,method){
    let overAllTotal=0;

    fetch(url, {
        method: method,// Specify the HTTP method
        credentials: "include"

    }).then(response => response.json())
        .then(books => {
            const tbody = document.getElementById('books-list');
            const total = document.createElement("h2");

            tbody.innerHTML = ''; // clear existing rows if any
            books.forEach(book => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${book.bookTitle}</td>
                    <td>${book.bookAuthor}</td>
                    <td>${book.quantity}</td>
                    <td>${book.price * book.quantity}</td>`;

                tbody.appendChild(row);

                overAllTotal +=  book.price*book.quantity;
            });
            total.innerHTML = `<p><b>Total: </b>${overAllTotal}</p>`;

            tbody.appendChild(total);

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


