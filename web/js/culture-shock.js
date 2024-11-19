const time =  [1,2,3,4, 5,6,7,   8,   9,   10, 11, 12,13,14,15,16,17,18,19,20,21,  22,23,24,25,26,27,28, 29,30];
const curve = [0,4,7,9,11,8,2,-3.5,-9.5,-11.5,-12,-10,-8,-6,-3, 1, 3, 2, 1,-2,-3,-2.5,-1, 0, 1, 0,-1, 0,0.3, 0];

new Chart("fullChart", {
    type: "line",
    data: {
        labels: time,
        datasets: [{
            data: curve,
            borderColor: "blue",
            borderJoinStyle: "bevel",
            borderWidth: 5,
            pointRadius: 0,
            fill: false,
        }]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            xAxes:[{
                ticks: {
                    display: false
                }
            }],
            yAxes: [{
                ticks: {
                    display: false,
                    beginAtZero: true
                }
            }]
        }
    }
});