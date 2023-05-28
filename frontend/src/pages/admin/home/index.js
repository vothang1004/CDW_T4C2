import React from "react";
import PrivateRoute from "../../../components/route/PrivateRoute";
import AdminRequiredRoute from "../../../components/route/AdminRequiredRoute";
import AdminLayout from "../../../components/layouts/admin/AdminLayout";
import { Container, Paper } from "@mui/material";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);
export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "Chart.js Bar Chart",
    },
  },
};

const labels = ["January", "February", "March", "April", "May", "June", "July"];

export const data = {
  labels,
  datasets: [
    {
      label: "Dataset 1",
      data: labels.map(() => Math.random() * 100),
      backgroundColor: "rgba(255, 99, 132, 0.5)",
    },
    {
      label: "Dataset 2",
      data: labels.map(() => Math.random() * 100),
      backgroundColor: "rgba(53, 162, 235, 0.5)",
    },
  ],
};

function AdminHome() {
  return (
    <PrivateRoute>
      <AdminRequiredRoute>
        <AdminLayout>
          <Paper sx={{ height: "100%" }}>
            <Bar options={options} data={data} />
          </Paper>
        </AdminLayout>
      </AdminRequiredRoute>
    </PrivateRoute>
  );
}

export default AdminHome;
