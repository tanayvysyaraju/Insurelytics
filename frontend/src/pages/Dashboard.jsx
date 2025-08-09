import React, { useEffect, useState } from 'react';
import axios from '../api/axiosClient';
import { Card, CardContent, Typography, Grid } from '@mui/material';
import { BarChart, Bar, XAxis, YAxis, Tooltip, PieChart, Pie, Cell, Legend } from 'recharts';

export default function Dashboard() {
  const [data, setData] = useState(null);

  useEffect(() => {
    async function fetchSummary() {
      const res = await axios.get('/dashboard/summary');
      setData(res.data);
    }
    fetchSummary();
  }, []);

  if (!data) return <div>Loading...</div>;

  // Prepare chart data
  const barData = [
    { name: 'Total Invested', value: parseFloat(data.totalInvestment) },
    { name: 'Current Value', value: parseFloat(data.currentValue) },
    { name: 'Upcoming Premiums', value: parseFloat(data.upcomingPremiums) },
    { name: 'Projected Growth', value: parseFloat(data.projectedGrowth) },
  ];
  const pieData = [
    { name: 'Current Value', value: parseFloat(data.currentValue) },
    { name: 'Projected Growth', value: parseFloat(data.projectedGrowth) },
  ];
  const COLORS = ['#8884d8', '#82ca9d'];

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Account Summary
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6">Totals</Typography>
              <Typography>Total Invested: ${data.totalInvestment}</Typography>
              <Typography>Current Value: ${data.currentValue}</Typography>
              <Typography>Upcoming Premiums: ${data.upcomingPremiums}</Typography>
              <Typography>Projected Growth: ${data.projectedGrowth}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6">Bar Chart</Typography>
              <BarChart width={400} height={250} data={barData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="value" />
              </BarChart>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6">Allocation</Typography>
              <PieChart width={400} height={250}>
                <Pie
                  dataKey="value"
                  data={pieData}
                  cx="50%"
                  cy="50%"
                  outerRadius={80}
                  label
                >
                  {pieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Legend />
                <Tooltip />
              </PieChart>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </div>
  );
}