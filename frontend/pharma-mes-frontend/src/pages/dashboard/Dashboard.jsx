import { useEffect, useState } from "react";

import {
    Grid,
    Typography
} from "@mui/material";

import MainLayout from "../../layouts/MainLayout";
import StatCard from "../../components/dashboard/StatCard";

import { getAllBatches } from "../../services/batchService";

function Dashboard() {

    const [batches, setBatches] = useState([]);

    const [error, setError] = useState("");

    useEffect(() => {

        const loadDashboardData = async () => {

            try {

                const batchData =
                    await getAllBatches();

                setBatches(batchData);

            } catch (error) {

                console.error(
                    "Dashboard loading failed:",
                    error
                );

                setError(
                    "Unable to load dashboard data"
                );
            }
        };

        loadDashboardData();

    }, []);

    return (

        <MainLayout>

            <Typography
                variant="h4"
                fontWeight="bold"
                gutterBottom
            >
                MES Dashboard
            </Typography>

            {error && (
                <Typography
                    color="error"
                    sx={{ mb: 2 }}
                >
                    {error}
                </Typography>
            )}

            <Grid
                container
                spacing={3}
                sx={{ mt: 1 }}
            >

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                    <StatCard
                        title="Total Batches"
                        value={batches.length}
                        subtitle="Retrieved from MES"
                    />
                </Grid>

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                    <StatCard
                        title="Production Orders"
                        value="—"
                        subtitle="Coming next"
                    />
                </Grid>

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                    <StatCard
                        title="Quality Inspections"
                        value="—"
                        subtitle="Coming next"
                    />
                </Grid>

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                    <StatCard
                        title="Notifications"
                        value="—"
                        subtitle="Coming next"
                    />
                </Grid>

            </Grid>

        </MainLayout>
    );
}

export default Dashboard;