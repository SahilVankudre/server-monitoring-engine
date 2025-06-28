async function fetchServerData() {
    const serverResponse = await fetch("/api/servers");
    const servers = await serverResponse.json();
  
    const tbody = document.querySelector("#server-table tbody");
    tbody.innerHTML = "";
  
    servers.forEach(server => {
      const row = `<tr>
        <td>${server.id}</td>
        <td>${server.name}</td>
        <td>${server.ipAddress}</td>
        <td>${server.status}</td>
        <td>${server.responseTimeMs}</td>
        <td>${server.lastChecked}</td>
      </tr>`;
      tbody.insertAdjacentHTML("beforeend", row);
    });
  
    updateChart(servers); // optional charting using latest response times
  }
  
  function updateChart(servers) {
    const labels = servers.map(s => s.name);
    const data = servers.map(s => s.responseTimeMs);
  
    chart.data.labels = labels;
    chart.data.datasets[0].data = data;
    chart.update();
  }
  
  // Dummy history data simulation — replace with backend API call when available
  async function fetchHistoryData() {
    const dummyHistory = [
      { recordId: 1, serverId: 1, name: "Google", ip: "8.8.8.8", healthy: true, response: 632, timestamp: "2025-05-08 00:55:07" },
      { recordId: 2, serverId: 1, name: "Google", ip: "8.8.8.8", healthy: true, response: 350, timestamp: "2025-05-08 00:55:28" }
    ];
  
    const tbody = document.querySelector("#history-table tbody");
    tbody.innerHTML = "";
  
    dummyHistory.forEach(record => {
      const row = `<tr>
        <td>${record.recordId}</td>
        <td>${record.serverId}</td>
        <td>${record.name}</td>
        <td>${record.ip}</td>
        <td>${record.healthy}</td>
        <td>${record.response}</td>
        <td>${record.timestamp}</td>
      </tr>`;
      tbody.insertAdjacentHTML("beforeend", row);
    });
  }
  
  // Line chart config
  const ctx = document.getElementById('historyChart').getContext('2d');
  const chart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: [],
      datasets: [{
        label: 'Current Response Time (ms)',
        data: [],
        borderColor: '#00ffcc',
        fill: false,
        tension: 0.1
      }]
    },
    options: {
      responsive: true,
      scales: {
        x: { ticks: { color: "#fff" } },
        y: { ticks: { color: "#fff" } }
      },
      plugins: {
        legend: {
          labels: { color: "#fff" }
        }
      }
    }
  });
  
  function autoRefresh() {
    fetchServerData();
    fetchHistoryData();
    setTimeout(autoRefresh, 5000); // Refresh every 5 seconds
  }
  
  autoRefresh();
  