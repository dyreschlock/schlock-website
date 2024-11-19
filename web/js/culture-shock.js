var c1 = 'rgba(204,  51, 158, 1)';
var c2 = 'rgba(100, 113, 180, 1)';
var c3 = 'rgba(  0, 125, 179, 1)';
var c4 = 'rgba(  0, 138,   0, 1)';

var b1 = 'rgba(227, 195, 217, 0.5)';
var b2 = 'rgba(195, 195, 227, 0.5)';
var b3 = 'rgba(195, 217, 227, 0.5)';
var b4 = 'rgba(195, 227, 195, 0.5)';

const time =      [   1,   2,   3,   4,   5, 6, 7,   8,   9,   10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,  22, 23, 24, 25, 26, 27, 28, 29, 30, 31];
const p1_curve  = [   0,   4,   7,   9,  11];
const p2_curve  = [null,null,null,null,  11,   8,  2, -3.5,-9.5,-11.5];
const p3_curve  = [null,null,null,null,null,null,null,null,null,-11.5,-12, -10,  -8,  -6, -3];
const p4_curve  = [null,null,null,null,null,null,null,null,null,null,null,null,null,null, -3,  1,  3,  2,  1, -2, -3,-2.5, -1,  0,  1,  0, -1,  0,0.3,  0,  0];

const curve =     [ 0, 4, 7, 9,11, 8, 2,-3.5,-9.5,-11.5,-12,-10, -8, -6, -3,  1,  3,  2,  1, -2, -3,-2.5, -1,  0,  1,  0, -1,  0,0.3,  0,  0];

new Chart("fullChart", {
    type: "line",
    data: {
        labels: time,
        datasets: [
            {
                data: p1_curve,
                borderColor: c1,
                backgroundColor: b1,
                borderWidth: 5,
                pointRadius: 0,
                fill: true,
            },
            {
                data: p2_curve,
                borderColor: c2,
                backgroundColor: b2,
                borderWidth: 5,
                pointRadius: 0,
                fill: true,
            },
            {
                data: p3_curve,
                borderColor: c3,
                backgroundColor: b3,
                borderWidth: 5,
                pointRadius: 0,
                fill: true,
            },
            {
                data: p4_curve,
                borderColor: c4,
                backgroundColor: b4,
                borderWidth: 5,
                pointRadius: 0,
                fill: true,
            },
        ]
    },
    options: {
        legend: {
            display: false
        },
        scales: {
            xAxes:[{
                gridLines: {
                    color: "#ccc",
                },
                ticks: {
                    display: false,
                }
            }],
            yAxes: [{
                gridLines: {
                    color: "#ccc",
                    zeroLineWidth: 2,
                    zeroLineColor: "black",
                },
                ticks: {
                    display: false,
                }
            }]
        }
    }
});