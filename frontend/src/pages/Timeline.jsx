import React, { useEffect, useState } from 'react';
import axios from '../api/axiosClient';
import { Typography, TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody } from '@mui/material';

export default function Timeline() {
  const [entries, setEntries] = useState([]);

  useEffect(() => {
    async function fetchTimeline() {
      const res = await axios.get('/policies/timeline');
      // Sort by date
      const sorted = res.data.sort((a, b) => new Date(a.date) - new Date(b.date));
      setEntries(sorted);
    }
    fetchTimeline();
  }, []);

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Policy Timeline
      </Typography>
      <TableContainer component={Paper} sx={{ maxWidth: 700 }}>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>Policy ID</TableCell>
              <TableCell>Label</TableCell>
              <TableCell>Date</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {entries.map((entry, index) => (
              <TableRow key={index}>
                <TableCell>{entry.policyId}</TableCell>
                <TableCell>{entry.label}</TableCell>
                <TableCell>{new Date(entry.date).toLocaleDateString()}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}