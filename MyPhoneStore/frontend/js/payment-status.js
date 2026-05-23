document.addEventListener("DOMContentLoaded", function () {
    const currentUrl = window.location.href;

    const urlParams = new URLSearchParams(currentUrl.split('?')[1]);

    const vnpAmount = urlParams.get("vnp_Amount");
    const vnpPayDate = urlParams.get("vnp_PayDate");
    const vnpTxnRef = urlParams.get("vnp_TxnRef");
    const vnpResponseCode = urlParams.get("vnp_ResponseCode");

    document.getElementById("vnp-amount").textContent = `Số tiền: ${(vnpAmount / 100).toLocaleString()} VND`;
    document.getElementById("vnp-paydate").textContent = `Thời gian: ${formatDate(vnpPayDate)}`;
    document.getElementById("vnp-txnref").textContent = `Mã giao dịch: ${vnpTxnRef}`;
    document.getElementById("vnp-responsecode").textContent = `Trạng thái: ${vnpResponseCode === "00" ? "Thành công" : "Thất bại"}`;
    
    const statusImage = document.getElementById("status-image");
    if (vnpResponseCode === "00") {
        statusImage.src = "../images/success.png";
        statusImage.alt = "Giao dịch thành công";
    } else {
        statusImage.src = "../images/fail.png";
        statusImage.alt = "Giao dịch thất bại";
    }
});

function formatDate(vnpPayDate) {
    if (!vnpPayDate) return "Không xác định";
    const year = vnpPayDate.substring(0, 4);
    const month = vnpPayDate.substring(4, 6);
    const day = vnpPayDate.substring(6, 8);
    const hour = vnpPayDate.substring(8, 10);
    const minute = vnpPayDate.substring(10, 12);
    const second = vnpPayDate.substring(12, 14);
    return `${hour}:${minute}:${second}, ${day}/${month}/${year}`;
}