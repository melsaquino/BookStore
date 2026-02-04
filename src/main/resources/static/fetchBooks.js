const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
let currentPage = 0;

function fetchBasedOnEndPoints( url,method){
    const container = document.getElementById("books-container");
    const userId = container.dataset.userId;

    fetch(url, {
        method: method,// Specify the HTTP method
        credentials: "include"

    }).then(response => response.json())
        .then(books => {
            const tbody = document.getElementById('books-list');
            tbody.innerHTML = ''; // clear existing rows if any
            books.forEach(book => {
                const row = document.createElement('tr');
                const csrfInput = document.createElement("input");
                csrfInput.type = "hidden";
                csrfInput.name = "_csrf";
                csrfInput.value = csrfToken;
                const form = document.createElement('form');
                form.method = 'post';
                form.action = `/add_cart/${userId}/${book.isbn}`;
                form.appendChild(csrfInput);

                const button = document.createElement('button');
                button.type = 'submit';
                button.textContent = 'Add to cart now!';

                form.appendChild(button);
                row.innerHTML = `
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.description}</td>
                    <td>${book.price}</td>
                    <td>${book.category}</td>
                    <td>${book.stock}</td>`;

                const td = document.createElement('td');
                td.appendChild(form);
                row.appendChild(td);
                tbody.appendChild(row);

            });
        })
        .catch(error => {
            console.error('Error loading books:', error);
        });
}
function  fetchAllBooks(page=0) {
    currentPage =page;
        fetchBasedOnEndPoints(`/api/books?page=${page}`,"GET");


}
fetchAllBooks();
document.getElementById("nextBtn").addEventListener("click", () => {
    fetchAllBooks(currentPage + 1);
});
document.getElementById("prevBtn").addEventListener("click", () => {
    if(currentPage>0)
        fetchAllBooks(currentPage - 1);
});

document.getElementById("book-filter").addEventListener("submit", function(event) {
    event.preventDefault(); // prevent page reload
    // Grab input values
    const container = document.getElementById("books-container");
    const userId = container.dataset.userId;
    const category = document.getElementById("category").value;
    const author = document.getElementById("author").value.trim();
    const priceRange = document.getElementById("priceRange").value;

    // Construct URL with query parameters
    const params = new URLSearchParams();
    if (author) params.append("author", author);
    if (priceRange) params.append("priceRange", priceRange);
    if (category) params.append("category", category);

    const url = `/api/books/filtered?${params.toString()}`;
    fetchBasedOnEndPoints(url, "GET");
})
document.getElementById("search-bar").addEventListener("submit", function(event) {
    event.preventDefault(); // prevent page reload
    // Grab input values
    const container = document.getElementById("books-container");
    const userId = container.dataset.userId;
    const query = document.getElementById("query").value;

    // Construct URL with query parameters
    const params = new URLSearchParams();
    if (query) params.append("query", query);

    const url = `/api/books/search?${params.toString()}`;
    fetchBasedOnEndPoints(url, "GET");
    
});


