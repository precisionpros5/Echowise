var amount = 0;
const upiID = "smethilesh4-1@oksbi";
function calculateTip() {
    let billAmount = parseFloat(document.getElementById("billAmount").value);
    let serviceRating = parseInt(document.getElementById("serviceRating").value);
    let numPeople = parseInt(document.getElementById("numPeople").value);

    if (isNaN(billAmount) || billAmount <= 0) {
        alert("Please enter a valid bill amount.");
        return;
    }
    if (isNaN(numPeople) || numPeople <= 0) {
        alert("Please enter a valid number of people.");
        return;
    }

    let tipPercentage = serviceRating / 100;
    let tipAmount = billAmount * tipPercentage;
    let totalAmount = billAmount + tipAmount;
    let shareAmount = totalAmount / numPeople;

    document.getElementById("tipAmount").innerHTML = `Tip Amount:${tipAmount.toFixed(2)}`;
    document.getElementById("totalAmount").innerHTML = `Total Bill Amount: ${totalAmount.toFixed(2)}`;
    document.getElementById("shareAmount").innerHTML = `Per Person Share: ${shareAmount.toFixed(2)}`;
    amount = shareAmount.toFixed(2);
    document.getElementById("gpay").style.display = "block";

    generateUPIQRCode(upiID, amount);
}

document.getElementById("serviceRating").addEventListener("input", function () {
    let rating = parseInt(this.value);
    let emoji = "😡";
    if (rating >= 2) emoji = "😠";
    if (rating >= 4) emoji = "😐";
    if (rating >= 6) emoji = "🙂";
    if (rating >= 8) emoji = "😊";
    if (rating >= 10) emoji = "😍";

    document.getElementById("emoji").innerText = emoji;
    document.getElementById("slider_value").innerHTML = rating;
});

function generateUPIQRCode(upiID, amount) {
    let upiURL = `upi://pay?pa=${upiID}&pn=Merchant&mc=&tid=&tr=&tn=Payment&am=${amount}&cu=INR`;
    document.getElementById("qrcode").innerHTML = "";
    new QRCode(document.getElementById("qrcode"), upiURL);
}
