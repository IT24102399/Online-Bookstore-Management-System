/**
 * Online Bookstore Management System
 * Custom JavaScript for enhanced user experience
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Initialize popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });

    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
        alerts.forEach(function(alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);

    // Add fade-in animation to main content
    const mainContent = document.querySelector('main');
    if (mainContent) {
        mainContent.classList.add('fade-in');
    }

    // Add active class to current nav item
    const currentLocation = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentLocation) {
            link.classList.add('active');
        }
    });

    // Form validation
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });

    // Quantity input validation
    var quantityInputs = document.querySelectorAll('input[type="number"]');
    quantityInputs.forEach(function(input) {
        input.addEventListener('change', function() {
            var min = parseInt(this.getAttribute('min') || 1);
            var max = parseInt(this.getAttribute('max') || 100);
            var value = parseInt(this.value);

            if (value < min) {
                this.value = min;
            } else if (value > max) {
                this.value = max;
            }

            // If this is a cart quantity input, update totals
            if (this.classList.contains('cart-quantity')) {
                updateCartItem(this);
            }
        });
    });

    // Confirm delete actions
    var deleteButtons = document.querySelectorAll('.btn-delete, .delete-btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            if (!confirm('Are you sure you want to delete this item?')) {
                event.preventDefault();
            }
        });
    });

    // Book rating system
    const ratingInputs = document.querySelectorAll('.rating-input');
    if (ratingInputs.length > 0) {
        ratingInputs.forEach(input => {
            input.addEventListener('change', function() {
                const ratingValue = document.querySelector('.rating-value');
                if (ratingValue) {
                    ratingValue.textContent = this.value;
                }
            });
        });
    }

    // Search functionality enhancement
    const searchForm = document.querySelector('.search-form');
    if (searchForm) {
        const searchInput = searchForm.querySelector('input[type="search"]');
        if (searchInput) {
            const clearButton = document.createElement('button');
            clearButton.type = 'button';
            clearButton.className = 'btn-close search-clear';
            clearButton.setAttribute('aria-label', 'Clear search');
            clearButton.style.display = 'none';
            clearButton.style.position = 'absolute';
            clearButton.style.right = '50px';
            clearButton.style.top = '50%';
            clearButton.style.transform = 'translateY(-50%)';

            searchForm.style.position = 'relative';
            searchForm.appendChild(clearButton);

            searchInput.addEventListener('input', function() {
                clearButton.style.display = this.value ? 'block' : 'none';
            });

            clearButton.addEventListener('click', function() {
                searchInput.value = '';
                clearButton.style.display = 'none';
                searchInput.focus();
            });
        }
    }

    // Order tracking progress bar
    const orderStatusElement = document.querySelector('.order-status');
    if (orderStatusElement) {
        const status = orderStatusElement.dataset.status;
        const progressBar = document.querySelector('.progress-bar');

        if (progressBar) {
            let progressWidth = 0;

            switch(status) {
                case 'PENDING':
                    progressWidth = 25;
                    break;
                case 'PROCESSING':
                    progressWidth = 50;
                    break;
                case 'SHIPPED':
                    progressWidth = 75;
                    break;
                case 'DELIVERED':
                    progressWidth = 100;
                    break;
                default:
                    progressWidth = 0;
            }

            progressBar.style.width = progressWidth + '%';
            progressBar.setAttribute('aria-valuenow', progressWidth);
        }
    }
});

/**
 * Update cart item quantity and price
 * @param {HTMLElement} input - The quantity input element
 */
function updateCartItem(input) {
    const itemRow = input.closest('tr');
    if (!itemRow) return;

    const priceElement = itemRow.querySelector('.item-price');
    const totalElement = itemRow.querySelector('.item-total');
    const cartTotalElement = document.querySelector('.cart-total');

    if (priceElement && totalElement) {
        const price = parseFloat(priceElement.dataset.price || priceElement.textContent.replace('$', ''));
        const quantity = parseInt(input.value);

        if (quantity < 1) {
            input.value = 1;
            return;
        }

        const total = price * quantity;
        totalElement.textContent = '$' + total.toFixed(2);

        // Update cart total
        if (cartTotalElement) {
            let cartTotal = 0;
            const allTotals = document.querySelectorAll('.item-total');
            allTotals.forEach(el => {
                cartTotal += parseFloat(el.textContent.replace('$', ''));
            });

            cartTotalElement.textContent = '$' + cartTotal.toFixed(2);
        }

        // If there's a form to update the cart, submit it
        const updateForm = itemRow.querySelector('.update-cart-form');
        if (updateForm) {
            updateForm.submit();
        }
    }
}
