<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Shopping Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .cart-item-img {
                width: 80px;
                height: 80px;
                object-fit: cover;
            }
            .list-group-item {
                border: none;
                padding: 10px 15px;
                border-bottom: 1px solid #e9ecef;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/snackbar.jsp"/>
        <div class="container my-5">
            <h2 class="text-center mb-4">My Shopping Cart</h2>
            <div class="row">
                <!-- Cart Summary Section -->
                <div class="col-md-6">
                    <h5>Summary</h5>
                    <div class="card mb-4">
                        <ul class="cart-body list-group list-group-flush">
                            <c:forEach items="${cart.cartItems}" var="cartItem">
                                <li class="list-group-item">
                                    <div class="row align-items-center">
                                        <div class="col-3 d-flex justify-content-center">
                                            <img src="${cartItem.dish.imageUrl ? cartItem.dish.imageUrl : 'img/fast-food-image.jpg'}" alt="${cartItem.dish.dishName}" class="cart-item-img">
                                        </div>
                                        <div class="col-9">
                                            <div class="w-100 d-flex justify-content-between align-items-center">
                                                <h6 class="mb-1 flex-grow-1">${cartItem.dish.dishName}</h6>
                                                <h6 class="text-primary">$${cartItem.totalPrice}</h6>
                                            </div>
                                            <small>Category: ${cartItem.dish.category.categoryName}</small>
                                            <div class="mt-2 w-100 d-flex justify-content-between align-items-center">
                                                <a href="/cart/delete?cartItemId=${cartItem.cartItemId}" class="text-danger flex-grow-1 fw-bold" >Remove</a>
                                                <div class="input-group w-auto align-items-center">
                                                    <a href="/cart/update?cartItemId=${cartItem.cartItemId}&quantity=${cartItem.quantity - 1}" class="btn btn-lg rounded-circle" type="button">-</a>
                                                    <input type="text" class="form-control text-center rounded" value="${cartItem.quantity}" readonly style="max-width: 40px; max-height: 40px">
                                                    <a href="/cart/update?cartItemId=${cartItem.cartItemId}&quantity=${cartItem.quantity + 1}" class="btn btn-lg rounded-circle" type="button">+</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <h5>Order Summary</h5>
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="order-summary">
                                <p>Subtotal: <span class="float-end"><strong>$${cart.totalAmount}</strong></span></p>
                                <p>Delivery: <span class="float-end"><strong>Free</strong></span></p>
                                <p>Tax: <span class="float-end"><strong>Free</strong></span></p>
                                <p>Voucher: <span class="float-end"><strong>$<span id="discount-price">0.0</span></strong></span></p>
                                <hr>
                                <p class="mb-0"><strong>Total:</strong> <span class="float-end"><strong>$<span id="total-price">${cart.totalAmount}</span></strong></span></p>
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header fw-bold">Select Voucher</div>
                        <div class="card-body">
                            <select id="select-voucher" class="form-control">
                                <option value="0" discount-data="0">--- Choose voucher ---</option>
                                <c:forEach items="${vouchers}" var="voucher">
                                    <option value="${voucher.voucherId}" discount-data="${voucher.discountPercentage}">
                                        ${voucher.name} (Sale: ${voucher.discountPercentage}%)
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="button" class="btn btn-success float-end mt-2" value="Apply" />
                        </div>
                    </div>
                </div>
                <!-- Checkout Form Section -->
                <div class="col-md-6">
                    <div class="card">
                        <form action="/order/checkout" method="post" class="card-body">
                            <input type="hidden" name="voucherId" id="voucherId" value="0"/>
                            <div class="mb-3">
                                <h5>How Would You Like to Receive Your Order</h5>
                                <div class="btn-group w-100" role="group">
                                    <input type="radio" class="btn-check" value="Cash" name="paymentMethod" id="delivery" autocomplete="off" checked>
                                    <label class="btn btn-outline-primary" for="delivery">Pay by cash</label>
                                    <input type="radio" class="btn-check" value="Online" name="paymentMethod" id="cod" autocomplete="off">
                                    <label class="btn btn-outline-primary" for="cod">Pay online</label>
                                </div>
                            </div>
                            <br/>
                            <div>
                                <h5>Enter Your Address</h5>

                                <div class="mb-3">
                                    <label class="form-label">City</label>
                                    <select class="form-select" name="city" id="city" aria-label=".form-select-sm">
                                        <option value="" selected>Chọn tỉnh thành</option>           
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">District</label>
                                    <select class="form-select" name="district" id="district" aria-label=".form-select-sm">
                                        <option value="" selected>Chọn quận huyện</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Ward</label>
                                    <select class="form-select" name="ward" id="ward" aria-label=".form-select-sm">
                                        <option value="" selected>Chọn phường xã</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Specific Address</label>
                                    <input class="form-control" type="text" name="specific" placeholder="Số nhà, tên đường"/>
                                </div>
                                <button type="submit" class="btn btn-success w-100">Place Order</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const voucherSelect = document.querySelector("select[id='select-voucher']");
                const applyButton = document.querySelector(".btn.btn-success");
                const discountPriceSpan = document.getElementById("discount-price");
                const totalAmountSpan = document.getElementById("total-price");
                const subtotal = parseFloat(${cart.totalAmount});

                applyButton.addEventListener("click", function () {
                    const selectedOption = voucherSelect.options[voucherSelect.selectedIndex];
                    const discountPercentage = parseFloat(selectedOption.getAttribute("discount-data")) || 0;

                    const discountAmount = (subtotal * discountPercentage) / 100;
                    const newTotal = subtotal - discountAmount;

                    discountPriceSpan.textContent = discountAmount.toFixed(1);
                    totalAmountSpan.textContent = newTotal.toFixed(1);
                    document.getElementById("voucherId").value = selectedOption.getAttribute("value");
                });
            });
        </script>
        <script>
            var citis = document.getElementById("city");
            var districts = document.getElementById("district");
            var wards = document.getElementById("ward");
            var Parameter = {
                url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
                method: "GET",
                responseType: "application/json",
            };
            var promise = axios(Parameter);
            promise.then(function (result) {
                renderCity(result.data);
            });

            function renderCity(data) {
                for (const x of data) {
                    citis.options[citis.options.length] = new Option(x.Name, x.Name);
                }
                citis.onchange = function () {
                    district.length = 1;
                    ward.length = 1;
                    if (this.value != "") {
                        const result = data.filter(n => n.Name === this.value);
                        for (const k of result[0].Districts) {
                            district.options[district.options.length] = new Option(k.Name, k.Name);
                        }
                    }
                };
                district.onchange = function () {
                    ward.length = 1;
                    const dataCity = data.filter((n) => n.Name === citis.value);
                    if (this.value != "") {
                        const dataWards = dataCity[0].Districts.filter(n => n.Name === this.value)[0].Wards;

                        for (const w of dataWards) {
                            wards.options[wards.options.length] = new Option(w.Name, w.Name);
                        }
                    }
                };
            }
        </script>
    </body>
</html>
