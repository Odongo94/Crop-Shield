package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object AgriEconomicsCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "agri_econ_micro",
            pillar = CurriculumPillarType.AGRI_ECONOMICS,
            code = "5.a-f",
            title = "Microeconomics of Agriculture & Production Theory",
            summary = "Market equilibrium, price elasticity of demand and supply, neoclassical production functions (Stages I, II, III), short-run and long-run cost curves, profit maximization (MR=MC), and input pricing.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ae_demand_supply_elasticity",
                    code = "a-b",
                    title = "Demand, Supply & Elasticities in Agriculture",
                    coreDefinition = "The market mechanics of agricultural commodities, shifts in curves, and quantitative price, income, and cross-price responsiveness.",
                    keyMechanismsAndFormulas = listOf(
                        "Market Equilibrium: Quantity Demanded (Q_d) = Quantity Supplied (Q_s); price at which clearance occurs without surplus or shortage.",
                        "Price Elasticity of Demand (Ed): Ed = (% ΔQ_d) / (% ΔP) = (dQ/dP) x (P/Q). Agricultural staples generally exhibit highly inelastic demand (|Ed| < 1.0).",
                        "Income Elasticity of Demand (Ey): Ey = (% ΔQ) / (% ΔIncome); Engel's Law states that as household income rises, the proportion spent on food declines.",
                        "King's Law / Farmer Paradox: A bumper agricultural crop often reduces total farm revenues because percentage price drops exceed percentage yield gains due to inelastic demand."
                    ),
                    detailedElaboration = "Agricultural markets display unique economic phenomena. Because primary food staples (maize, rice, wheat) are biological necessities with few direct non-food substitutes, their demand is inelastic (|Ed| < 1). Consequently, modest supply shocks (drought or record bumper harvests) cause disproportionately wild price volatility. Agricultural supply is also price-inelastic in the immediate short run due to biological gestation lags: once a crop is planted, farmers cannot instantly expand or halt production in response to daily market price fluctuations.",
                    commonExamQuestions = listOf(
                        "Demonstrate mathematically why a bumper harvest can lead to reduced gross revenue for cereal farmers under inelastic demand.",
                        "Distinguish between a change in demand (shift of curve) versus a change in quantity demanded (movement along curve) for fertilizer.",
                        "State Engel's Law and explain its implications for long-term agricultural share in national economies."
                    ),
                    practicalTakeaway = "To avoid the trap of King's Law during bumper harvest seasons, invest in postharvest hermetic grain storage or value-addition processing to defer commodity sales until market prices rebound."
                ),
                CurriculumSubtopic(
                    id = "ae_production_cost_theory",
                    code = "c-f",
                    title = "Production Functions, Cost Curves & Rational Stage II",
                    coreDefinition = "The economic optimization of agricultural inputs to maximize net enterprise returns based on physical production relationships and cost structures.",
                    keyMechanismsAndFormulas = listOf(
                        "The Classical Production Function: Y = f(X1 | X2, ... Xn); Total Physical Product (TPP), Marginal Physical Product (MPP = dTPP/dX), Average Physical Product (APP = TPP/X).",
                        "The Three Stages of Production: Stage I (MPP > APP, irrational; efficiency of fixed inputs rising); Stage II (MPP < APP and MPP > 0, rational economic operating zone); Stage III (MPP < 0, irrational negative returns).",
                        "Profit-Maximizing Input Level: VMP = MFC (Value of Marginal Product = Marginal Factor Cost), where VMP = MPP x Price of Output.",
                        "Cost Equations: Total Cost (TC) = Total Fixed Cost (TFC) + Total Variable Cost (TVC); Marginal Cost (MC) = dTC/dQ; Profit maximized where Marginal Revenue (MR) = MC.",
                        "Shutdown vs. Break-Even Price: Shutdown occurs when Price < minimum Average Variable Cost (AVC); Break-even occurs when Price = minimum Average Total Cost (ATC)."
                    ),
                    detailedElaboration = "A rational agricultural producer will always operate strictly within Stage II of the production function—beginning where APP is at its maximum (MPP = APP) and ending where TPP reaches its maximum (MPP = 0). Operating in Stage I is irrational because fixed resources are under-utilized. In Stage III, adding more input (such as excessive nitrogen fertilizer burning roots) actually reduces total yield. Under perfect competition, the farm maximizes profit by equating the value of marginal product to the input price (P_y x MPP_x = P_x). In the short run, a farm should continue operating even at a loss as long as gross revenue covers all variable costs (P > AVC), contributing partially to fixed overhead.",
                    commonExamQuestions = listOf(
                        "Define the boundaries of Stage II of the classical production function and explain why a rational manager never operates in Stage I or Stage III.",
                        "Differentiate the firm's short-run shutdown price point from the long-run break-even price point.",
                        "Calculate the optimal nitrogen fertilizer application rate given TPP = 20X + 10X² - X³, price of maize = $0.20/kg, and price of nitrogen = $1.50/kg."
                    ),
                    practicalTakeaway = "If crop market price crashes below Average Variable Cost (AVC)—such that harvest labor and transport exceed market price—cease operations immediately to minimize losses; but if price covers AVC, continue harvesting to pay down fixed equipment loans."
                )
            ),
            examFocusPoints = listOf(
                "Characteristics and boundaries of the 3 Stages of Production",
                "Profit-maximizing optimality conditions: MR = MC and VMP = MFC",
                "Price elasticity formulas and Engel's Law in agricultural commodities"
            ),
            practicalFieldApplications = listOf(
                "Calculating enterprise enterprise gross margins (Gross Margin = Gross Income - Total Variable Costs)",
                "Conducting break-even yield and break-even price calculations for crop budgets"
            ),
            recommendedTextbookIds = listOf("book_drummond_agri_economics", "book_varian_microeconomics", "book_debertin_agri_production")
        ),
        CurriculumTopic(
            id = "agri_econ_macro_marketing",
            pillar = CurriculumPillarType.AGRI_ECONOMICS,
            code = "5.g-l",
            title = "Macroeconomics & Agricultural Marketing",
            summary = "National income accounting (GDP, GNP), fiscal policy (subsidies, taxation), monetary policy (interest rates, credit access), inflation, marketing margins, and supply chains.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ae_macroeconomics_policy",
                    code = "g-k",
                    title = "National Income, Inflation & Fiscal/Monetary Policy",
                    coreDefinition = "The macroeconomic environment influencing agricultural sector investment, input cost inflation, rural credit availability, and commodity trade.",
                    keyMechanismsAndFormulas = listOf(
                        "GDP by Expenditure Approach: GDP = C + I + G + (X - M) [Consumption + Investment + Government Spending + (Exports - Imports)].",
                        "Agricultural GDP Contribution: Percentage of real GDP originating from primary agriculture, livestock, fisheries, and forestry.",
                        "Inflation & Agricultural Terms of Trade (TOT): TOT = (Price Index of Agricultural Exports / Price Index of Manufactured Imports) x 100.",
                        "Monetary Policy Transmission: Central Bank increases policy interest rates -> commercial banks raise lending rates -> agricultural credit contracts -> farm capital machinery purchases decline."
                    ),
                    detailedElaboration = "Macroeconomic policies heavily impact farm profitability. Subsidies on agricultural inputs (e.g., electronic fertilizer vouchers) shift farm supply curves outward by lowering marginal costs. Expansionary monetary policy with lower interest rates promotes long-term capital investments in on-farm irrigation, storage silos, and mechanization. Inflation increases the cost of imported inputs (petroleum, active pesticide ingredients, fertilizers) faster than farm-gate commodity prices adjust, squeezing farm operating margins.",
                    commonExamQuestions = listOf(
                        "Explain how an increase in the Central Bank's discount rate influences commercial agricultural lending and farm capital formation.",
                        "Analyze the economic welfare impacts (consumer surplus, producer surplus, government expenditure) of an agricultural fertilizer subsidy.",
                        "What is disguised unemployment / underemployment in rural agrarian economies and how does it affect marginal productivity of labor?"
                    ),
                    practicalTakeaway = "Hedge against seasonal input price inflation by pre-booking and purchasing seed and fertilizer during the off-season when manufacturer discounts and liquidity are favorable."
                ),
                CurriculumSubtopic(
                    id = "ae_marketing_channels_margins",
                    code = "l",
                    title = "Agricultural Marketing Channels & Price Spreads",
                    coreDefinition = "The commercial activities and institutional channels involved in moving farm products from the farm gate to the ultimate consumer.",
                    keyMechanismsAndFormulas = listOf(
                        "Marketing Margin (Price Spread): Margin = Retail Consumer Price (Pr) - Farm-Gate Producer Price (Pf); Farmer's Share of Consumer Dollar (%) = (Pf / Pr) x 100.",
                        "Marketing Utilities: Form Utility (processing wheat to flour), Place Utility (transporting from rural farm to urban center), Time Utility (grain storage from harvest to lean season), Possession Utility (wholesaling and retail transactions).",
                        "Market Structure-Conduct-Performance (SCP) Paradigm: Evaluates market competitiveness from monopoly to pure competition."
                    ),
                    detailedElaboration = "Agricultural marketing is complicated by product perishability, bulkiness, seasonal supply spikes, and geographic dispersion of producers. Middlemen and aggregators perform vital functions (assembling, grading, transport, storage, and financing), but long intermediary chains can reduce the farmer's share of the consumer dollar to below 25%. Producer cooperatives and digital commodity exchanges (e.g., warehouse receipt systems) empower smallholders to bypass predatory spot brokers, aggregate volumes, and secure competitive bids.",
                    commonExamQuestions = listOf(
                        "Identify the four primary marketing utilities and give concrete agricultural examples of each.",
                        "Calculate the Farmer's Share of the consumer dollar if retail price of milk is $1.20/L and farm-gate payout is $0.42/L.",
                        "Describe the operational mechanism and producer benefits of a Warehouse Receipt System (WRS)."
                    ),
                    practicalTakeaway = "Join farmer producer organizations (FPOs) to pool harvest volumes: volume aggregation transforms smallholders from price-takers into collective price-negotiators and reduces per-unit freight costs."
                )
            ),
            examFocusPoints = listOf(
                "Calculation of Marketing Margins and Farmer's Share of Consumer Dollar",
                "The four marketing utilities: Form, Place, Time, and Possession",
                "Impact of input subsidies vs. price floors on agricultural market equilibrium"
            ),
            practicalFieldApplications = listOf(
                "Calculating marketing price spreads across supply chain nodes from farm-gate to urban supermarket",
                "Conducting farm enterprise sensitivity analysis against fuel inflation and interest rate hikes"
            ),
            recommendedTextbookIds = listOf("book_kohls_uhl_agri_marketing", "book_drummond_agri_economics")
        )
    )
}
