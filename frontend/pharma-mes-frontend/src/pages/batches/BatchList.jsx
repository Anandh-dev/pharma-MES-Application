import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Alert,
    Box,
    Button,
    CircularProgress,
    FormControl,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@mui/material";

import MainLayout from "../../layouts/MainLayout";

import { getAllBatches } from "../../services/batchService";

function BatchList() {

    const navigate = useNavigate();

    const [batches, setBatches] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [search, setSearch] = useState("");

    const [statusFilter, setStatusFilter] = useState("ALL");

    useEffect(() => {

        const loadBatches = async () => {

            try {

                setLoading(true);
                setError("");

                const data = await getAllBatches();

                console.log("Batches loaded:", data);

                setBatches(data);

            } catch (error) {

                console.error(
                    "Failed to load batches:",
                    error.response?.data || error.message
                );

                setError("Unable to load batch data.");

            } finally {

                setLoading(false);

            }
        };

        loadBatches();

    }, []);

    const statuses = useMemo(() => {

        const uniqueStatuses = batches
            .map((batch) => batch.status)
            .filter(Boolean);

        return [...new Set(uniqueStatuses)];

    }, [batches]);

    const filteredBatches = useMemo(() => {

        const searchValue = search
            .trim()
            .toLowerCase();

        return batches.filter((batch) => {

            const matchesSearch =
                !searchValue ||
                String(batch.batchId)
                    .toLowerCase()
                    .includes(searchValue) ||
                String(batch.batchNumber || "")
                    .toLowerCase()
                    .includes(searchValue);

            const matchesStatus =
                statusFilter === "ALL" ||
                batch.status === statusFilter;

            return matchesSearch && matchesStatus;

        });

    }, [batches, search, statusFilter]);

    return (
        <MainLayout>

            <Box>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Batch Management
                </Typography>

                    <Button
                    variant="contained"
                    onClick={() => navigate("/batches/create")}
                >
                    + Create Batch
                </Button>

                <Typography
                    variant="body1"
                    color="text.secondary"
                    sx={{ mb: 3 }}
                >
                    View and manage manufacturing batches.
                </Typography>

                {!loading && !error && (
                    <Box
                        sx={{
                            display: "flex",
                            gap: 2,
                            mb: 3,
                            flexWrap: "wrap"
                        }}
                    >

                        <TextField
                            label="Search Batch"
                            placeholder="ID or batch number"
                            value={search}
                            onChange={(event) =>
                                setSearch(event.target.value)
                            }
                            sx={{
                                minWidth: 280
                            }}
                        />

                        <FormControl
                            sx={{
                                minWidth: 180
                            }}
                        >

                            <InputLabel>
                                Status
                            </InputLabel>

                            <Select
                                value={statusFilter}
                                label="Status"
                                onChange={(event) =>
                                    setStatusFilter(event.target.value)
                                }
                            >

                                <MenuItem value="ALL">
                                    All
                                </MenuItem>

                                {statuses.map((status) => (

                                    <MenuItem
                                        key={status}
                                        value={status}
                                    >
                                        {status}
                                    </MenuItem>

                                ))}

                            </Select>

                        </FormControl>

                    </Box>
                )}

                {loading && (
                    <Box
                        sx={{
                            display: "flex",
                            justifyContent: "center",
                            mt: 5
                        }}
                    >
                        <CircularProgress />
                    </Box>
                )}

                {error && (
                    <Alert
                        severity="error"
                        sx={{ mb: 3 }}
                    >
                        {error}
                    </Alert>
                )}

                {!loading && !error && (
                    <TableContainer
                        component={Paper}
                        elevation={2}
                    >

                        <Table>

                            <TableHead>

                                <TableRow>

                                    <TableCell>
                                        ID
                                    </TableCell>

                                    <TableCell>
                                        Batch Number
                                    </TableCell>

                                    <TableCell>
                                        Production Order
                                    </TableCell>

                                    <TableCell>
                                        Recipe
                                    </TableCell>

                                    <TableCell>
                                        Planned Quantity
                                    </TableCell>

                                    <TableCell>
                                        Status
                                    </TableCell>

                                </TableRow>

                            </TableHead>

                            <TableBody>

                                {filteredBatches.map((batch) => (

                                    <TableRow
                                        key={batch.batchId}
                                        hover
                                    >

                                        <TableCell>
                                            {batch.batchId}
                                        </TableCell>

                                        <TableCell>

                                            <Button
                                                variant="text"
                                                onClick={() =>
                                                    navigate(`/batches/${batch.batchId}`)
                                                }
                                            >
                                            {batch.batchNumber}
                                            </Button>

                                        </TableCell>

                                        <TableCell>
                                            {batch.productionOrderId}
                                        </TableCell>

                                        <TableCell>
                                            {batch.recipeId}
                                        </TableCell>

                                        <TableCell>
                                            {batch.plannedQuantity}
                                        </TableCell>

                                        <TableCell>
                                            {batch.status}
                                        </TableCell>

                                    </TableRow>

                                ))}

                                {filteredBatches.length === 0 && (

                                    <TableRow>

                                        <TableCell
                                            colSpan={6}
                                            align="center"
                                        >
                                            No batches found
                                        </TableCell>

                                    </TableRow>

                                )}

                            </TableBody>

                        </Table>

                    </TableContainer>
                )}

                {!loading && !error && (
                    <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ mt: 2 }}
                    >
                        Showing {filteredBatches.length} of{" "}
                        {batches.length} batches
                    </Typography>
                )}

            </Box>

        </MainLayout>
    );
}

export default BatchList;