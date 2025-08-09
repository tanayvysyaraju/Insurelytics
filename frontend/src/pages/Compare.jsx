import React, { useEffect, useState } from 'react';
import axios from '../api/axiosClient';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Checkbox,
  Button,
  Typography,
} from '@mui/material';

export default function Compare() {
  const [policies, setPolicies] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [comparison, setComparison] = useState(null);

  useEffect(() => {
    async function fetchPolicies() {
      const res = await axios.get('/policies');
      setPolicies(res.data);
    }
    fetchPolicies();
  }, []);

  const handleSelect = (id) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((p) => p !== id) : [...prev, id]
    );
  };

  const handleCompare = async () => {
    const query = selectedIds.join(',');
    const res = await axios.get(`/policies/compare?ids=${query}`);
    setComparison(res.data);
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Compare Policies
      </Typography>
      <TableContainer component={Paper} sx={{ maxWidth: 700 }}>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell>Name</TableCell>
              <TableCell>Type</TableCell>
              <TableCell>Premium</TableCell>
              <TableCell>Duration</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {policies.map((p) => (
              <TableRow key={p.id}>
                <TableCell padding="checkbox">
                  <Checkbox
                    checked={selectedIds.includes(p.id)}
                    onChange={() => handleSelect(p.id)}
                  />
                </TableCell>
                <TableCell>{p.name}</TableCell>
                <TableCell>{p.type}</TableCell>
                <TableCell>${p.premium}</TableCell>
                <TableCell>{p.duration} yrs</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Button
        variant="contained"
        disabled={selectedIds.length < 2}
        onClick={handleCompare}
        sx={{ mt: 2 }}
      >
        Compare Selected
      </Button>
      {comparison && (
        <TableContainer component={Paper} sx={{ maxWidth: 700, mt: 3 }}>
          <Table size="small">
            <TableHead>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>Type</TableCell>
                <TableCell>Premium</TableCell>
                <TableCell>Duration</TableCell>
                <TableCell>Expected Return</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {comparison.map((c) => (
                <TableRow key={c.id}>
                  <TableCell>{c.name}</TableCell>
                  <TableCell>{c.type}</TableCell>
                  <TableCell>${c.premium}</TableCell>
                  <TableCell>{c.duration} yrs</TableCell>
                  <TableCell>${c.expectedReturn}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </div>
  );
}