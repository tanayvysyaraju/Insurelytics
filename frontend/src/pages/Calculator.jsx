import React, { useState } from 'react';
import axios from '../api/axiosClient';
import { Card, CardContent, TextField, Button, Typography, Stack } from '@mui/material';
import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';

export default function Calculator() {
  const [amount, setAmount] = useState('1000');
  const [years, setYears] = useState('5');
  const [rate, setRate] = useState('0.05');
  const [risk, setRisk] = useState('1.0');
  const [results, setResults] = useState(null);

  const handleCalculate = async () => {
    const res = await axios.post('/calculator/returns', {
      amount: parseFloat(amount),
      years: parseInt(years, 10),
      rate: parseFloat(rate),
      riskAdjustment: parseFloat(risk),
    });
    // Convert map to array of objects for recharts
    const data = Object.entries(res.data.returns).map(([year, value]) => ({
      year: parseInt(year, 10),
      value: parseFloat(value),
    }));
    setResults(data);
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Return Calculator
      </Typography>
      <Card sx={{ maxWidth: 500 }}>
        <CardContent>
          <Stack spacing={2}>
            <TextField
              label="Amount"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              type="number"
            />
            <TextField
              label="Years"
              value={years}
              onChange={(e) => setYears(e.target.value)}
              type="number"
            />
            <TextField
              label="Rate (e.g. 0.05 for 5%)"
              value={rate}
              onChange={(e) => setRate(e.target.value)}
              type="number"
            />
            <TextField
              label="Risk Adjustment"
              value={risk}
              onChange={(e) => setRisk(e.target.value)}
              type="number"
            />
            <Button variant="contained" onClick={handleCalculate}>
              Calculate
            </Button>
          </Stack>
        </CardContent>
      </Card>
      {results && (
        <Card sx={{ marginTop: 3 }}>
          <CardContent>
            <Typography variant="h6">Projected Returns</Typography>
            <LineChart width={500} height={300} data={results}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="year" label={{ value: 'Year', position: 'insideBottom', offset: -5 }} />
              <YAxis label={{ value: 'Value', angle: -90, position: 'insideLeft' }} />
              <Tooltip />
              <Line type="monotone" dataKey="value" stroke="#8884d8" />
            </LineChart>
          </CardContent>
        </Card>
      )}
    </div>
  );
}