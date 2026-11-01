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
                    id = "ae_demand_supply",
                    code = "a",
                    title = "Demand, Supply & Market Equilibrium",
                    coreDefinition = "The fundamental microeconomic mechanics governing agricultural commodity price formation, shifts in supply/demand curves, market clearance, and welfare distributions (consumer and producer surplus).",
                    keyMechanismsAndFormulas = listOf(
                        "Market Equilibrium: Quantity Demanded (Q_d) = Quantity Supplied (Q_s); price at which the market clears without unintended surplus or shortage.",
                        "Linear Market Model: Q_d = a - bP and Q_s = c + dP; Equilibrium Price P* = (a - c) / (b + d); Equilibrium Quantity Q* = a - bP*.",
                        "Consumer Surplus (CS) & Producer Surplus (PS): CS = area beneath demand curve and above market price; PS = area above supply curve and beneath market price. Total Economic Welfare = CS + PS.",
                        "Cobweb Theorem: Explains cyclical price fluctuations in agricultural markets with biological production lags. Convergent cobweb occurs when supply elasticity is less than demand elasticity (|Es| < |Ed|); divergent when |Es| > |Ed|."
                    ),
                    detailedElaboration = "Agricultural markets display unique economic phenomena because agricultural commodities are biological, seasonal, and weather-dependent. The Law of Demand dictates an inverse relationship between price and quantity demanded (downward-sloping curve), driven by substitution and income effects. The Law of Supply dictates a direct relationship between price and quantity supplied (upward-sloping curve).\n\nShifts vs. Movements: A change in price causes a movement ALONG the curve (change in quantity demanded/supplied). A change in non-price determinants (consumer income, input costs, rainfall, technological breakthroughs, export tariffs) shifts the ENTIRE curve outward or inward. In agriculture, weather shocks cause severe leftward supply shifts, triggering sharp price spikes due to inelastic demand. The Cobweb Model illustrates how farmers make planting decisions based on current harvest prices: if current prices are high, farmers overplant next season, resulting in an unexpected supply glut that crashes prices in the subsequent harvest period.",
                    biologicalAndChemicalDetails = listOf(
                        "Biological Gestation Lags: Unlike manufacturing where factory output can adjust overnight, tree crops (coffee, cocoa, avocado) require 3 to 5 years from planting to initial bearing, creating multi-year structural lag cycles in supply response.",
                        "Perishability and Forced Market Clearance: Fresh horticultural perishables (tomatoes, leafy greens) lack physiological postharvest shelf life without cold chains, forcing producers to accept whatever price is offered on the day of harvest (vertical instantaneous supply curve)."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Calculating Farm-Gate Equilibrium Price and Minimum Floor Viability: 1) Collect 12-month historical wholesale prices (P) and traded volumes (Q) from regional terminal wholesale markets; 2) Estimate baseline local supply using seasonal planting census data; 3) Compute average transport, loading, and broker commissions per metric ton; 4) Subtract total marketing intermediary deductions from wholesale market clearing price to establish the net effective farm-gate equilibrium price; 5) Compare net farm-gate price against enterprise variable production costs to verify operational solvency."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Supply Glut Distress Collapse: Rapid accumulation of unsold produce at rural assembly points, wholesale price plummeting below harvest labor cost, farmers leaving produce unpicked in fields.",
                        "Speculative Hoarding Shortage: Sharp divergence between farm-gate payout and urban consumer retail prices; wholesalers holding grain in dry storage awaiting anticipated seasonal price escalation."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: In a regional grain market, the monthly demand and supply functions for dry maize grain are given by:\nDemand: Q_d = 1,400 - 4P\nSupply: Q_s = 200 + 8P\nwhere Q is in metric tons (MT) and P is the price per metric ton in US Dollars ($).\n1) Calculate the market equilibrium price (P*) and equilibrium quantity (Q*).\n2) If the government introduces a price floor of $120/MT to support farmers, calculate the resulting market surplus and the government expenditure required to purchase the excess grain.\n\nSolution:\n1) At equilibrium, Q_d = Q_s:\n1,400 - 4P = 200 + 8P\n1,200 = 12P => P* = $100/MT.\nEquilibrium Quantity Q* = 1,400 - 4(100) = 1,000 MT.\n\n2) Under price floor P = $120/MT:\nQuantity Demanded Q_d = 1,400 - 4(120) = 1,400 - 480 = 920 MT.\nQuantity Supplied Q_s = 200 + 8(120) = 200 + 960 = 1,160 MT.\nMarket Surplus = Q_s - Q_d = 1,160 - 920 = 240 MT.\nGovernment Fiscal Outlay = 240 MT x $120/MT = $28,800."
                    ),
                    commonExamQuestions = listOf(
                        "Demonstrate mathematically and graphically why a bumper harvest can lead to reduced gross farm revenues under inelastic agricultural demand.",
                        "Distinguish between a change in demand versus a change in quantity demanded in the context of fertilizer application.",
                        "Explain the Cobweb Theorem and determine the mathematical condition under which price fluctuations converge to stable equilibrium."
                    ),
                    practicalTakeaway = "During expected regional bumper harvest gluts, never sell fresh produce directly at harvest; utilize hermetic grain storage bags or postharvest cold storage to defer sales until seasonal prices rebound by 30–50%.",
                    authoritativeTextbookRef = "Agricultural Economics (Drummond & Goodwin, Pearson, Ch. 3); Microeconomic Theory (Varian, Norton, Ch. 1)"
                ),
                CurriculumSubtopic(
                    id = "ae_elasticities",
                    code = "b",
                    title = "Elasticities of Demand and Supply",
                    coreDefinition = "The quantitative measurement of the responsiveness of quantity demanded or supplied to changes in price, consumer income, or prices of related goods.",
                    keyMechanismsAndFormulas = listOf(
                        "Price Elasticity of Demand (Ed): Ed = (% ΔQ_d) / (% ΔP) = (dQ/dP) x (P/Q). Point elasticity vs. Arc (midpoint) elasticity: Ed = [(Q2 - Q1) / ((Q1 + Q2)/2)] / [(P2 - P1) / ((P1 + P2)/2)].",
                        "Elasticity Classification: Inelastic (|Ed| < 1); Unitary (|Ed| = 1); Elastic (|Ed| > 1); Perfectly Inelastic (Ed = 0); Perfectly Elastic (Ed = ∞).",
                        "Income Elasticity of Demand (Ey): Ey = (% ΔQ) / (% ΔIncome). Normal goods (Ey > 0); Inferior goods (Ey < 0); Luxury goods (Ey > 1); Essential food staples (0 < Ey < 1).",
                        "Cross-Price Elasticity (Exy): Exy = (% ΔQ_x) / (% ΔP_y). Substitutes (Exy > 0, e.g., maize and sorghum); Complements (Exy < 0, e.g., tractor fuel and mechanical tillage).",
                        "Engel's Law: As household real income rises, the proportion of income spent on food decreases, even if absolute food expenditure increases."
                    ),
                    detailedElaboration = "Price elasticity is the central determinant of farm revenue dynamics. For primary food staples (rice, maize, potatoes, wheat), demand is highly price-inelastic (|Ed| between 0.2 and 0.5) because food is an absolute physiological necessity with no non-food substitutes. Under King's Law (the Farmer's Paradox), a general bumper crop reduces aggregate farm income: because demand is inelastic, a 10% increase in harvest volume requires a 25% drop in price to clear the market, causing total revenue (P x Q) to contract sharply.\n\nAgricultural supply elasticity is highly time-dependent: in the immediate market period (day of harvest), supply is completely fixed and perfectly inelastic (Es = 0). In the short run (one season), supply is slightly elastic as farmers can vary fertilizer or weeding intensity. In the long run (multi-year), supply is relatively elastic as farmers adjust capital investments, land clearing, and perennial orchard plantings.",
                    biologicalAndChemicalDetails = listOf(
                        "Human Caloric Saturation: The human stomach has a fixed daily biological capacity (approx. 2,000–2,500 kcal/day), limiting total physical food intake and rendering the price elasticity of bulk caloric staples strictly inelastic across human populations.",
                        "Biological Substitutability: When the price of nitrogen fertilizer spikes, farmers partially substitute legume cover crops or animal manure, demonstrating positive cross-price elasticity among agricultural inputs."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Measuring Price Elasticity of Farm Produce: 1) Record weekly sales volumes and prices for a specialty farm product (e.g., organic strawberries) across 8 consecutive weeks; 2) Apply the Arc Midpoint Formula to price and volume shifts; 3) If |Ed| > 1.0 (elastic), test promotional 10% price discounts to expand total gross revenue; 4) If |Ed| < 1.0 (inelastic), maintain premium pricing and focus on value-addition packaging rather than price discounting."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Inelastic Market Collapse: Extreme price volatility where a modest 5% regional overproduction causes a catastrophic 40% crash in farm-gate prices.",
                        "Giffen Good Indicator: Extreme poverty scenario where an increase in the price of the staple food (e.g., cassava or potato) forces families to cut out meat and buy even more of the cheap staple."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural cooperative observes that when the farm-gate price of cabbage is $0.80/kg, local farmers sell 5,000 kg per week. When a regional drought reduces supply and the price rises to $1.20/kg, quantity purchased by wholesalers contracts to 4,000 kg per week.\n1) Calculate the Price Elasticity of Demand using the Midpoint (Arc) Formula.\n2) Classify the elasticity and determine whether total revenue increased or decreased.\n\nSolution:\n1) Midpoint Arc Elasticity Formula:\nΔQ = 4,000 - 5,000 = -1,000 kg\nAverage Q = (5,000 + 4,000) / 2 = 4,500 kg\n% ΔQ = -1,000 / 4,500 = -0.2222 (-22.22%)\n\nΔP = $1.20 - $0.80 = +$0.40/kg\nAverage P = ($0.80 + $1.20) / 2 = $1.00/kg\n% ΔP = 0.40 / 1.00 = +0.4000 (+40.00%)\n\nEd = (% ΔQ) / (% ΔP) = -0.2222 / 0.4000 = -0.556.\nAbsolute value |Ed| = 0.556 (Inelastic).\n\n2) Revenue Analysis:\nInitial Revenue TR1 = 5,000 kg x $0.80 = $4,000.\nNew Revenue TR2 = 4,000 kg x $1.20 = $4,800.\nBecause demand is inelastic (|Ed| < 1), the percentage price increase (+40%) exceeded the percentage quantity decline (-22.2%), increasing total revenue by $800."
                    ),
                    commonExamQuestions = listOf(
                        "State Engel's Law and explain its macroeconomic implications for the declining share of agriculture in growing national economies.",
                        "Explain why cross-price elasticity of demand between maize and chemical nitrogen fertilizer is negative, while between maize and sorghum it is positive.",
                        "Using the total revenue test, describe the relationship between price changes and total revenue for elastic versus inelastic goods."
                    ),
                    practicalTakeaway = "If you produce a commodity with inelastic demand (staple cereals), never compete on volume expansion alone; diversify into high-value horticultural crops with income-elastic demand (avocados, berries, cut flowers).",
                    authoritativeTextbookRef = "Principles of Agricultural Economics (Barkley & Barkley, Routledge, Ch. 4); Economics of Agricultural Development (Norton et al., Ch. 2)"
                ),
                CurriculumSubtopic(
                    id = "ae_production_analysis",
                    code = "c",
                    title = "Production Analysis & the Classical Production Function",
                    coreDefinition = "The physical input-output relationship in agricultural enterprise, the Law of Diminishing Marginal Returns, and the delineation of the Three Stages of Production.",
                    keyMechanismsAndFormulas = listOf(
                        "Neoclassical Production Function: Y = f(X1 | X2, X3, ... Xn), where Y is crop/animal yield, X1 is variable input (e.g., urea fertilizer), and X2...Xn are fixed inputs (e.g., land, machinery).",
                        "Product Measures: Total Physical Product (TPP = Y); Marginal Physical Product (MPP = dTPP/dX = rate of change in yield per unit input); Average Physical Product (APP = TPP / X = yield per unit of input).",
                        "Stage I: From X = 0 to where APP is at its maximum (MPP = APP). MPP > APP; Production Elasticity Ep > 1.0. Irrational stage: fixed resources underutilized.",
                        "Stage II (Rational Stage): From maximum APP (MPP = APP) to maximum TPP (MPP = 0). MPP < APP and MPP > 0; 0 <= Ep <= 1.0. Economic optimal application rate lies exclusively in this stage.",
                        "Stage III: Beyond maximum TPP (MPP < 0; Ep < 0). Irrational stage: additional input reduces absolute total output (e.g., fertilizer toxicity burning roots)."
                    ),
                    detailedElaboration = "The Law of Diminishing Marginal Returns is the bedrock of agricultural production theory. As successive equal increments of a variable input (nitrogen fertilizer, irrigation water, labor) are added to a fixed bundle of agricultural resources (one hectare of arable land), a point is eventually reached where the resulting increases in total yield (MPP) begin to diminish.\n\nBoundaries of the Three Stages:\n- Stage I extends from the origin to the point where APP peaks. In Stage I, the efficiency of the fixed resource (land) is rapidly rising, and the variable input's productivity is high. An agronomist should never stop adding inputs in Stage I because each additional bag of fertilizer continues to pull average yields upward.\n- Stage II begins where MPP intersects APP (at max APP) and ends where MPP hits zero (at maximum TPP). This is the only rational economic zone. The precise point within Stage II where profit is maximized depends on the prices of the crop and the input.\n- Stage III occurs when MPP becomes negative. Adding more variable input reduces total physical harvest (due to lodging, root toxicity, disease microclimates, or soil salt accumulation).",
                    biologicalAndChemicalDetails = listOf(
                        "Von Liebig's Law of the Minimum: Crop growth is controlled not by the total amount of resources available, but by the scarcest nutrient (limiting factor). Adding nitrogen when zinc is deficient produces zero MPP.",
                        "Mitscherlich-Spillman Yield Law: Crop yield response to fertilizer follows a diminishing increment curve: dy/dx = (A - y)c, where A is maximum potential yield and c is an efficiency constant."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Establishing an On-Farm Nitrogen Response Trial to Delineate Stage II: 1) Layout a Randomized Complete Block Design (RCBD) with 4 replications across 5 nitrogen levels (0, 50, 100, 150, 200 kg N/ha); 2) Maintain all other agronomic factors (P, K, weed control, plant density) uniform; 3) Weigh grain harvest at 14% moisture from central 10 m² of each plot; 4) Fit a polynomial regression curve (Y = b0 + b1X - b2X²); 5) Calculate first derivative to identify MPP and determine exact boundaries of Stage II."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Stage I Operating Error: Pale chlorotic crop canopy, low plant population density, unexploited genetic yield potential indicating under-application of variable inputs.",
                        "Stage III Toxicity Symptoms: Severe crop lodging in wheat/rice from excessive nitrogen, leaf tip necrosis, vegetative luxuriant growth with delayed reproductive flowering, increased fungal susceptibility."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A hybrid maize trial yields total physical product (TPP) in 50-kg bags per hectare as a function of urea application (X, in 50-kg bags/ha) according to:\nTPP = 18X + 6X² - 0.4X³\n1) Derive the mathematical equations for Marginal Physical Product (MPP) and Average Physical Product (APP).\n2) Determine the level of urea input (X) that marks the boundary between Stage I and Stage II (where APP is at maximum).\n3) Determine the level of urea input (X) that marks the boundary between Stage II and Stage III (where TPP is maximized).\n\nSolution:\n1) MPP and APP Derivation:\nMPP = d(TPP)/dX = 18 + 12X - 1.2X²\nAPP = TPP / X = 18 + 6X - 0.4X²\n\n2) Boundary between Stage I and Stage II:\nOccurs where MPP = APP (or d(APP)/dX = 0):\n18 + 12X - 1.2X² = 18 + 6X - 0.4X²\n6X - 0.8X² = 0 => X(6 - 0.8X) = 0\nX = 6 / 0.8 = 7.5 bags of urea/ha.\nAt X = 7.5 bags/ha, APP is maximized at 18 + 6(7.5) - 0.4(56.25) = 40.5 bags/ha.\n\n3) Boundary between Stage II and Stage III:\nOccurs where TPP is maximized, which means MPP = 0:\n18 + 12X - 1.2X² = 0\nDividing through by -1.2: X² - 10X - 15 = 0\nUsing quadratic formula: X = [10 ± √(100 - 4(1)(-15))] / 2 = [10 ± √(160)] / 2 = (10 + 12.65) / 2 = 11.32 bags/ha.\nTherefore, Stage II spans from X = 7.5 bags/ha to X = 11.32 bags/ha."
                    ),
                    commonExamQuestions = listOf(
                        "Define the three stages of the classical production function and explain why a rational farm manager never produces in Stage I or Stage III.",
                        "What is elasticity of production (Ep) and what are its numerical values at the boundaries of Stage II?",
                        "Relate the Law of Diminishing Marginal Returns to Von Liebig's Law of the Minimum in crop nutrition."
                    ),
                    practicalTakeaway = "Always operate within Stage II: never stop applying inputs while APP is still rising (Stage I), and never apply fertilizer to the point of negative marginal product (Stage III), regardless of how cheap the fertilizer is.",
                    authoritativeTextbookRef = "Agricultural Production Economics (David L. Debertin, Macmillan, Ch. 2-3); Farm Management (Kay, Edwards, Duffy, McGraw-Hill, Ch. 4)"
                ),
                CurriculumSubtopic(
                    id = "ae_cost_production",
                    code = "d-e",
                    title = "Cost of Production, Price & Output Determination",
                    coreDefinition = "The economic structure of short-run and long-run farm costs, profit-maximization conditions (MR = MC), and firm supply decisions (shutdown vs. break-even thresholds).",
                    keyMechanismsAndFormulas = listOf(
                        "Cost Classifications: Total Fixed Cost (TFC, incurred regardless of output e.g., land rent, depreciation, property tax); Total Variable Cost (TVC, varies with output e.g., seed, fertilizer, fuel, harvest labor); Total Cost (TC) = TFC + TVC.",
                        "Average & Marginal Costs: Average Fixed Cost (AFC = TFC/Q); Average Variable Cost (AVC = TVC/Q); Average Total Cost (ATC = TC/Q = AFC + AVC); Marginal Cost (MC = dTC/dQ = dTVC/dQ).",
                        "Profit-Maximizing Output Rule: A competitive price-taking farm maximizes profit (π = TR - TC) at the output level where Marginal Revenue equals Marginal Cost: MR = MC = P (with MC rising).",
                        "Short-Run Shutdown Rule: If market Price drops below minimum AVC (P < min AVC), the farm must shut down immediately to limit losses strictly to TFC. If min AVC <= P < min ATC, the farm should continue producing in the short run to cover all TVC and a portion of TFC.",
                        "Long-Run Break-Even Point: P = minimum ATC. If P < min ATC in the long run, the farm must exit the industry."
                    ),
                    detailedElaboration = "Agricultural cost curves reflect the underlying physical production functions inverted: when MPP is rising, MC is falling; when MPP reaches its peak, MC is at its minimum; when diminishing marginal returns set in and MPP declines, MC rises. The competitive farm's short-run supply curve is exactly identical to its Marginal Cost (MC) curve lying above the minimum Average Variable Cost (AVC) curve.\n\nFixed vs. Variable Cost Decisions: In agriculture, fixed costs (land mortgages, heavy machinery depreciation, establishment amortization of orchards) represent a very high proportion of total costs. When commodity prices crash, inexperienced managers often halt harvest immediately, which is an error if price still covers variable harvesting and transport costs (P > AVC). As long as P > AVC, every bushel harvested generates operating cash to pay down fixed bank debt.",
                    biologicalAndChemicalDetails = listOf(
                        "Biological Asset Depreciation: Orchards, dairy cows, and breeding sows represent biological capital assets whose biological depreciation follows non-linear survivorship curves requiring modified straight-line or MACRS economic amortization schedules.",
                        "Opportunity Cost of Farm Family Labor: The economic cost of unpaid family labor must be accounted for at its next best alternative off-farm earning wage to accurately measure economic profit versus accounting profit."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Enterprise Budgeting and Break-Even Price Calculation: 1) Itemize all operating inputs per hectare (seed, fertilizer, chemicals, fuel, labor) to determine Total Variable Cost (TVC); 2) Estimate expected yield (Q) under normal climate conditions; 3) Compute Break-Even Price to Cover Variable Costs: P_be_var = TVC / Q (minimum shutdown price); 4) Allocate overhead, land rent, and machinery depreciation to determine Total Fixed Cost (TFC); 5) Compute Total Break-Even Price: P_be_tot = (TFC + TVC) / Q; 6) Set forward contracts only at prices exceeding P_be_tot."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Cash-Flow Insolvency: Operational cash receipts insufficient to cover immediate weekly variable costs (fuel, feed, seasonal wages), leading to immediate farm shutdown.",
                        "Structural Balance Sheet Bankruptcy: Farm equity turning negative due to chronic operation below average total costs (P < ATC) over multi-year cycles."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial table egg producer has 10,000 laying hens with total fixed costs of $12,000 per month (housing, cage depreciation, loan interest). The total variable cost per month as a function of egg trays produced (Q, in trays of 30 eggs) is given by:\nTVC = 2.0Q + 0.0001Q²\n1) Derive the Marginal Cost (MC) and Average Variable Cost (AVC) equations.\n2) If the wholesale market price of an egg tray is $4.50 under perfect competition, calculate the profit-maximizing monthly output (Q*).\n3) Calculate total monthly profit (π).\n4) What is the absolute minimum shutdown price in the short run?\n\nSolution:\n1) Cost Functions:\nMC = d(TVC)/dQ = 2.0 + 0.0002Q\nAVC = TVC / Q = 2.0 + 0.0001Q\n\n2) Profit Maximization (MR = MC):\nIn competitive market, MR = P = $4.50:\n4.50 = 2.0 + 0.0002Q\n2.50 = 0.0002Q => Q* = 2.50 / 0.0002 = 12,500 trays/month.\n\n3) Profit Calculation:\nTotal Revenue TR = 12,500 x $4.50 = $56,250.\nTVC = 2.0(12,500) + 0.0001(12,500)² = 25,000 + 15,625 = $40,625.\nTotal Cost TC = TFC + TVC = $12,000 + $40,625 = $52,625.\nProfit π = TR - TC = $56,250 - $52,625 = +$3,625/month.\n\n4) Minimum Shutdown Price:\nOccurs at min AVC. Since AVC = 2.0 + 0.0001Q is linear and strictly increasing for Q >= 0, minimum AVC occurs as Q approaches 0, yielding min AVC = $2.00/tray. If egg price falls below $2.00/tray, the farm must cease production immediately."
                    ),
                    commonExamQuestions = listOf(
                        "Differentiate between the firm's short-run shutdown price point and long-run break-even price point.",
                        "Explain why the marginal cost (MC) curve intersects the AVC and ATC curves at their lowest points.",
                        "Explain why a farm operator should continue producing in the short run even when incurring economic losses, provided price exceeds AVC."
                    ),
                    practicalTakeaway = "Never abandon a ready crop in the field during a price slump if the market price exceeds harvest labor and freight costs: every dollar earned above AVC contributes directly to paying down bank mortgages.",
                    authoritativeTextbookRef = "Farm Management (Kay, Edwards, Duffy, McGraw-Hill, Ch. 5-7); Microeconomic Theory (Varian, Ch. 21-22)"
                ),
                CurriculumSubtopic(
                    id = "ae_input_demand",
                    code = "f",
                    title = "Production, Pricing & Demand for Inputs",
                    coreDefinition = "The economic theory of derived input demand, Value of Marginal Product (VMP), Marginal Factor Cost (MFC), and the Least-Cost Combination of multiple inputs.",
                    keyMechanismsAndFormulas = listOf(
                        "Value of Marginal Product (VMP): VMP = MPP_X x P_Y (Marginal Physical Product multiplied by the unit price of the crop output).",
                        "Marginal Factor Cost (MFC): MFC = P_X (unit cost or purchase price of the variable input in a competitive input market).",
                        "Optimal Single-Input Decision Rule: Profit is maximized where VMP equals MFC: VMP_X = MFC_X => MPP_X x P_Y = P_X => MPP_X = P_X / P_Y (input applied until MPP equals the input-to-output price ratio).",
                        "Least-Cost Combination of Two Inputs (Isoquant/Isocost Tangency): Marginal Rate of Technical Substitution MRTS_(X1, X2) = MPP_X1 / MPP_X2 = P_X1 / P_X2 => (MPP_X1 / P_X1) = (MPP_X2 / P_X2)."
                    ),
                    detailedElaboration = "The demand for agricultural inputs (seed, chemical fertilizer, irrigation water, tractor fuel, labor) is a derived demand: inputs are not purchased for direct consumer enjoyment, but for their capacity to produce a marketed agricultural commodity. A change in the consumer price of wheat directly shifts the derived demand curve for phosphate fertilizer.\n\nOptimal Input Allocation: As long as VMP > MFC, the revenue added by the last kilogram of fertilizer exceeds the cost of purchasing that kilogram, so applying more input increases total net farm profit. Profit peaks precisely where VMP = MFC. When choosing between two substitutable inputs (e.g., nitrogen from organic poultry manure vs. synthetic urea, or mechanical combine harvesting vs. manual labor), the least-cost combination requires equating the marginal physical product per dollar spent across all inputs.",
                    biologicalAndChemicalDetails = listOf(
                        "Biological Synergy / Nutrient Interaction: Applying phosphorus increases the marginal physical product of nitrogen (positive interaction on isoquant curvature), shifting the expansion path toward higher balanced nutrient uptake.",
                        "Diminishing Marginal Rate of Technical Substitution: As more machinery (capital) replaces manual weeding labor, each additional tractor-hour substitutes for progressively fewer laborers due to field geometry, headland turning, and crop injury constraints."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Determining Profit-Maximizing Fertilizer Application Rate: 1) Identify expected farm-gate crop price (P_y) and delivered fertilizer unit price (P_x); 2) Compute the input/output price ratio (P_x / P_y); 3) Obtain the empirical MPP function from local university trial data; 4) Equate MPP = P_x / P_y and solve for input rate X*; 5) Verify that the calculated X* lies strictly within Stage II of the production function."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Input Under-Utilization: Farm operating where VMP >> MFC; farmers leaving money on the table due to rural credit constraints or risk aversion.",
                        "Input Over-Utilization: Farm operating where VMP < MFC; fertilizer bills exceeding the value of incremental grain yield."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial potato grower determines that tuber yield (Y, in MT/ha) responds to potassium fertilizer (K, in 50-kg bags/ha) according to the production function:\nY = 12 + 4.0K - 0.2K²\nPotatoes sell at a wholesale price of P_y = $250/MT, and potassium fertilizer costs P_k = $50 per 50-kg bag.\n1) Determine the Marginal Physical Product (MPP) function.\n2) Calculate the profit-maximizing potassium application rate (K*).\n3) Calculate the maximum yield level and compare it to the profit-maximizing yield level.\n\nSolution:\n1) MPP Derivation:\nMPP = dY/dK = 4.0 - 0.4K (in MT of potatoes per bag of K).\n\n2) Profit-Maximizing Input Rule (VMP = MFC):\nVMP = MPP x P_y = (4.0 - 0.4K) x $250 = 1,000 - 100K.\nMFC = P_k = $50.\nEquating VMP = MFC:\n1,000 - 100K = 50\n100K = 950 => K* = 9.5 bags of potassium/ha.\n\n3) Yield Comparison:\nProfit-maximizing yield at K* = 9.5:\nY(9.5) = 12 + 4.0(9.5) - 0.2(9.5)² = 12 + 38 - 18.05 = 31.95 MT/ha.\nMaximum physical yield occurs where MPP = 0:\n4.0 - 0.4K = 0 => K_max = 10.0 bags/ha.\nY(10.0) = 12 + 4.0(10) - 0.2(100) = 12 + 40 - 20 = 32.00 MT/ha.\nNotice that profit is maximized at 9.5 bags/ha (31.95 MT/ha), NOT at maximum physical yield (10 bags/ha, 32.0 MT/ha). The last 0.5 bag of fertilizer costs $25 but only adds 0.05 MT of potatoes ($12.50), losing $12.50 in net profit."
                    ),
                    commonExamQuestions = listOf(
                        "Explain why profit-maximizing crop yield is always less than maximum physical yield whenever input prices are positive.",
                        "State the equimarginal principle as applied to allocating a limited operating capital budget across multiple crop enterprises.",
                        "Derive the least-cost combination condition for two variable inputs using isoquant and isocost geometry."
                    ),
                    practicalTakeaway = "Never aim for maximum physical yield: profit is maximized at a lower yield level where the last dollar spent on fertilizer earns exactly one dollar in crop revenue.",
                    authoritativeTextbookRef = "Agricultural Production Economics (Debertin, Ch. 4-6); Principles of Agricultural Economics (Barkley, Ch. 5)"
                )
            ),
            examFocusPoints = listOf(
                "Characteristics, boundaries, and economic rationality of the 3 Stages of Production",
                "Profit-maximizing optimality conditions: MR = MC for output and VMP = MFC for inputs",
                "Price elasticity formulas, King's Law, and Engel's Law in agricultural commodities",
                "Shutdown price (P = min AVC) vs. Break-even price (P = min ATC)"
            ),
            practicalFieldApplications = listOf(
                "Calculating enterprise gross margins (Gross Margin = Gross Income - Total Variable Costs)",
                "Conducting break-even yield and break-even price calculations for farm business plans",
                "Optimizing fertilizer blends based on least-cost combination principles"
            ),
            recommendedTextbookIds = listOf("book_drummond_agri_economics", "book_debertin_agri_production", "book_kay_farm_management")
        ),
        CurriculumTopic(
            id = "agri_econ_macro_marketing",
            pillar = CurriculumPillarType.AGRI_ECONOMICS,
            code = "5.g-l",
            title = "Macroeconomics, Agricultural Policy & Marketing",
            summary = "National income accounting (GDP, GNP), fiscal policy (subsidies, taxation), monetary policy (interest rates, credit access), inflation, marketing margins, and supply chains.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ae_national_income",
                    code = "g-h",
                    title = "National Income Accounting, Consumption & Investment",
                    coreDefinition = "The macroeconomic measurement of national economic performance (GDP, GNP, GNI), agricultural value added, the Keynesian consumption function, savings, and capital formation in agriculture.",
                    keyMechanismsAndFormulas = listOf(
                        "GDP by Expenditure Approach: GDP = C + I + G + (X - M) [Household Consumption + Gross Private Investment + Government Purchases + Net Exports].",
                        "Agricultural Value Added: Gross Value of Agricultural Output minus Cost of Intermediate Inputs Consumed (seed, fuel, fertilizer, agrochemicals).",
                        "Keynesian Consumption Function: C = a + bY_d, where 'a' is autonomous consumption, 'b' is the Marginal Propensity to Consume (MPC = ΔC / ΔY_d), and Y_d is disposable personal income.",
                        "Marginal Propensity to Save (MPS): MPS = ΔS / ΔY_d = 1 - MPC.",
                        "Keynesian Investment Multiplier (k): k = 1 / (1 - MPC) = 1 / MPS; ΔY = k x ΔI (a change in rural investment yields a multiplied increase in national income)."
                    ),
                    detailedElaboration = "Agriculture plays a foundational role in national economic development, providing food security, foreign exchange through exports, raw materials for agro-processing industries, and release of labor to industrial sectors (Kuznets' structural transformation). As national income grows, agriculture's percentage contribution to GDP inevitably declines, even as absolute agricultural output and productivity rise.\n\nRural Capital Formation & Investment: Capital investment in agriculture (on-farm irrigation networks, grain silos, cold chain logistics, farm mechanization) drives long-term total factor productivity (TFP). The Keynesian multiplier demonstrates that public infrastructure spending in rural areas (irrigation dams, farm-to-market roads) triggers cumulative rounds of income generation through local purchasing power.",
                    biologicalAndChemicalDetails = listOf(
                        "Biological Capital Growth: Unlike industrial machinery which depreciates from day one, biological capital assets (breeding herds, fruit orchards, timber plantations) appreciate in productive capacity over multi-year juvenile biological growth phases before reaching plateau maturity.",
                        "Natural Resource Capital Depletion: Unadjusted GDP fails to account for soil nutrient mining, groundwater overdraft, and deforestation; Green GDP incorporates environmental asset depreciation into national accounts."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Evaluating Farm Capital Investment using Net Present Value (NPV): 1) Project annual net cash inflows (CF_t) over useful asset life (e.g., 10-year solar drip irrigation system); 2) Determine appropriate discount rate (r, reflecting weighted cost of capital or commercial bank interest rate); 3) Compute Present Value of each year's cash flow: PV = CF_t / (1 + r)^t; 4) Sum all discounted PVs and subtract initial capital investment (C_0): NPV = Σ [CF_t / (1 + r)^t] - C_0; 5) Approve investment only if NPV > 0 and Internal Rate of Return (IRR) exceeds borrowing interest rate."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Rural Under-Investment Crisis: Decaying irrigation canals, high postharvest spoilage rates (>35%), reliance on manual hand-hoe tillage, low fertilizer adoption due to liquidity constraints.",
                        "Capital Flight from Rural Sector: Commercial bank deposits mobilized in rural farming communities being re-loaned to urban real estate rather than reinvested in farm modernization."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: In a developing agrarian economy, the national consumption function is estimated as:\nC = 200 + 0.80Y_d (in million US Dollars)\nThe Ministry of Agriculture launches a $50 million rural farm-to-market road and irrigation modernization program (ΔI = $50 million).\n1) Calculate the Marginal Propensity to Consume (MPC) and Marginal Propensity to Save (MPS).\n2) Calculate the Keynesian Investment Multiplier (k).\n3) Calculate the total resulting expansion in national income (ΔY).\n4) If the agricultural sector accounts for 30% of total national income, estimate the incremental income generated specifically within the agricultural economy.\n\nSolution:\n1) From C = 200 + 0.80Y_d:\nMPC (b) = 0.80\nMPS = 1 - MPC = 1 - 0.80 = 0.20.\n\n2) Investment Multiplier k:\nk = 1 / MPS = 1 / 0.20 = 5.0.\n\n3) Total Expansion in National Income (ΔY):\nΔY = k x ΔI = 5.0 x $50 million = $250 million.\n\n4) Incremental Agricultural Economy Income:\nΔY_agri = 30% x $250 million = $75 million."
                    ),
                    commonExamQuestions = listOf(
                        "Explain Simon Kuznets' concept of the structural transformation of agriculture during economic growth.",
                        "Define the Keynesian Investment Multiplier and explain how public investment in rural roads stimulates aggregate demand.",
                        "Differentiate between Gross Domestic Product (GDP) and Gross National Product (GNP) in an agrarian export economy."
                    ),
                    practicalTakeaway = "When appraising farm machinery purchases, never rely on simple payback period; always calculate Net Present Value (NPV) and Internal Rate of Return (IRR) to account for the time value of money and inflation.",
                    authoritativeTextbookRef = "Macroeconomics (Mankiw, Worth Publishers, Ch. 2-3); Agriculture and Economic Development (Johnston & Mellor, AER)"
                ),
                CurriculumSubtopic(
                    id = "ae_macro_policy",
                    code = "i-k",
                    title = "Unemployment, Inflation, Fiscal Policy & Monetary Policy",
                    coreDefinition = "The macroeconomic forces shaping rural labor markets, agricultural input cost inflation, terms of trade, central bank monetary policy, and public fiscal interventions.",
                    keyMechanismsAndFormulas = listOf(
                        "Disguised Unemployment / Underemployment: Structural situation in rural agrarian economies where surplus labor is employed on family farms such that the Marginal Productivity of Labor is near zero (MPP_L ≈ 0).",
                        "Agricultural Terms of Trade (TOT): TOT = (Index of Prices Received by Farmers / Index of Prices Paid by Farmers) x 100. A TOT < 100 indicates deteriorating farm purchasing power.",
                        "Fisher Equation (Real Interest Rate): r = i - π, where 'r' is the real interest rate, 'i' is the nominal interest rate, and 'π' is the inflation rate.",
                        "Fiscal Policy Levers: Targeted input subsidies (e-vouchers for fertilizer/seed), direct farm income support, rural infrastructure expenditure, import tariffs, and export taxes.",
                        "Monetary Policy Transmission: Central Bank increases policy interest rates -> commercial banks raise lending rates -> agricultural credit contracts -> farm capital machinery purchases decline."
                    ),
                    detailedElaboration = "Macroeconomic policies profoundly dictate farm profitability. High national inflation disproportionately harms farmers because imported agricultural inputs (petroleum diesel, active pesticide ingredients, synthetic fertilizers) escalate rapidly, while farm-gate commodity prices often lag behind due to concentrated intermediary wholesale buyer power.\n\nFiscal Policy: Government subsidies on certified seed and fertilizer shift the farm supply curve outward by lowering marginal costs of production. However, poorly targeted subsidies create fiscal deficits and risk market distortion. Monetary Policy: Agriculture is an inherently capital-intensive and debt-dependent industry (seasonal crop operating loans, multi-year machinery financing). When the Central Bank tightens monetary policy to combat inflation, soaring interest rates raise borrowing costs, triggering farm debt crises and discouraging adoption of capital-intensive irrigation technologies.",
                    biologicalAndChemicalDetails = listOf(
                        "Haber-Bosch Energy Link to Fertilizer Inflation: Nitrogen fertilizer synthesis requires natural gas (methane) as a hydrogen feedstock and energy source (consuming 3–5% of global natural gas); spikes in global hydrocarbon energy prices immediately drive domestic fertilizer inflation.",
                        "Biological Seasonality of Farm Labor Demand: Agriculture exhibits extreme seasonal peaks in labor demand (planting, weeding, harvesting) alternating with off-season disguised unemployment."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Conducting Farm Business Sensitivity Analysis Against Macroeconomic Shocks: 1) Model base farm budget; 2) Apply a 30% inflation shock to fuel and synthetic fertilizer prices; 3) Apply a 300 basis-point (3%) hike in agricultural bank borrowing rates; 4) Model a 15% drop in harvest commodity prices; 5) Calculate resulting impact on Net Farm Income and Debt Service Coverage Ratio (DSCR = Net Operating Income / Total Debt Service); 6) If DSCR < 1.25, restructure short-term debt into long-term fixed-rate instruments."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Deteriorating Terms of Trade Crisis: Farmers harvesting record bumper crops but finding that a bag of maize purchases only half the clothing, diesel, or medicine it bought three years prior.",
                        "Credit Rationing Squeeze: Rural commercial banks refusing agricultural loan applications due to central bank liquidity tightening and agricultural credit risk."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural economist tracks farm price indices in a cereal production province over two consecutive years:\nYear 1: Index of Prices Received for Paddy Rice = 120; Index of Prices Paid for Inputs = 110.\nYear 2: Due to a global energy crisis and fertilizer supply disruptions, the Index of Prices Paid for Inputs jumps to 154, while the Index of Prices Received for Paddy Rice rises to 132.\n1) Calculate the Agricultural Terms of Trade (TOT) for Year 1 and Year 2.\n2) Determine the percentage change in the farmer's purchasing power and interpret the economic policy implications.\n\nSolution:\n1) Terms of Trade Calculation:\nTOT = (Prices Received Index / Prices Paid Index) x 100\nYear 1 TOT = (120 / 110) x 100 = 109.09.\nYear 2 TOT = (132 / 154) x 100 = 85.71.\n\n2) Percentage Change in Purchasing Power:\n% ΔTOT = [(85.71 - 109.09) / 109.09] x 100 = -21.43%.\n\nInterpretation: In Year 1, farmers enjoyed favorable terms of trade (TOT = 109 > 100). In Year 2, despite farm-gate rice prices rising by 10% (from 120 to 132), input costs soared by 40% (from 110 to 154). The farmer's real purchasing power deteriorated by 21.43%, creating an acute farm income crisis requiring targeted emergency input subsidy or price stabilization intervention."
                    ),
                    commonExamQuestions = listOf(
                        "Define Arthur Lewis' concept of disguised unemployment and zero marginal productivity of labor in traditional agriculture.",
                        "Explain the Fisher Equation and describe how unanticipated inflation affects agricultural debtor farmers.",
                        "Analyze the economic welfare impacts (consumer surplus, producer surplus, government expenditure) of an agricultural fertilizer subsidy."
                    ),
                    practicalTakeaway = "Hedge against seasonal input price inflation by forward purchasing fertilizer and diesel during the off-season when manufacturer cash discounts and freight rates are at their annual lows.",
                    authoritativeTextbookRef = "Macroeconomics: Policy and Practice (Mishkin, Pearson, Ch. 5); Agricultural Price Policy for Developing Countries (Mellor & Ahmed, Johns Hopkins Univ. Press)"
                ),
                CurriculumSubtopic(
                    id = "ae_marketing",
                    code = "l",
                    title = "Agricultural Marketing, Supply Chains & Price Spreads",
                    coreDefinition = "The physical, economic, and institutional systems involved in moving agricultural commodities from the farm gate to final urban consumers, including the 4 utilities, marketing margins, and supply chain efficiency.",
                    keyMechanismsAndFormulas = listOf(
                        "The Four Marketing Utilities: Form Utility (processing paddy to polished rice), Place Utility (transporting grain from surplus rural regions to deficit urban centers), Time Utility (grain storage from harvest gluts to lean seasons), Possession Utility (wholesaling, retailing, and legal title transfer).",
                        "Absolute Marketing Margin (Price Spread): MM = P_r - P_f, where P_r is retail consumer price and P_f is farm-gate producer price.",
                        "Farmer's Share of Consumer Dollar (%): FS = (P_f / P_r) x 100.",
                        "Marketing Margin Percentage (%): MM% = (MM / P_r) x 100 = 100% - FS.",
                        "Shepherd's Index of Marketing Efficiency (ME): ME = (Total Value of Goods Sold / Total Marketing Costs and Margins) = V / I.",
                        "Warehouse Receipt System (WRS): Depositing certified grain in licensed warehouses to receive negotiable receipts used as bank loan collateral."
                    ),
                    detailedElaboration = "Agricultural marketing is inherently complex due to biological characteristics: bulkiness (low value-to-weight ratio), extreme perishability, spatial dispersion of smallholders, and seasonal production spikes clashing with continuous daily consumer demand. Intermediary actors (village assemblers, commission brokers, transporters, processors, wholesalers, retailers) perform indispensable marketing functions.\n\nMarketing Margins and Price Spreads: A wide marketing margin does not automatically signify middleman exploitation; it often reflects high real economic costs of refrigeration, long-distance transport over degraded roads, postharvest shrinkage (losses often reach 25–40% in perishables), packaging, and financing. However, in poorly integrated rural markets characterized by oligopsonistic broker cartels and asymmetric price information, smallholders receive a diminished share of the consumer dollar (often <25%). Farmer Producer Organizations (FPOs) and digital commodity exchanges empower smallholders to bypass predatory spot brokers, aggregate volumes, and achieve economies of scale.",
                    biologicalAndChemicalDetails = listOf(
                        "Postharvest Physiological Weight Loss (PWL): Fresh fruits and vegetables lose saleable mass continuously through respiration and moisture transpiration (1% moisture loss = 1% saleable weight reduction), requiring fast cold-chain transport to preserve marketing volume.",
                        "Ethylene Biosynthesis & Shelf-Life Management: Climacteric fruits (bananas, mangoes, tomatoes) emit ethylene during transit, requiring 1-methylcyclopropene (1-MCP) anti-ethylene fumigation or active ventilation to prevent premature ripening during transport."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Conducting a Crop Value Chain and Marketing Margin Audit: 1) Identify all intermediary nodes from field to supermarket (Producer -> Village Trader -> Wholesaler -> Retailer); 2) Record actual transaction prices at each exchange point; 3) Itemize specific marketing costs at each stage (sorting, bagging, freight, municipal market tax, spoilage write-offs); 4) Calculate gross margin and net profit margin for each actor; 5) Compute the Farmer's Share of the Consumer Dollar; 6) Identify primary structural bottlenecks causing wide price spreads."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Asymmetric Price Transmission ('Rockets and Feathers'): Retail food prices shoot up like rockets when farm-gate prices rise, but float down like feathers when farm-gate prices collapse.",
                        "Oligopsonistic Broker Collusion: A small syndicate of wholesale buyers agreeing on a uniform, non-negotiable below-market price floor when meeting unorganized farmers at rural assembly markets."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial tomato grower produces high-grade fresh tomatoes. The supply chain data per 20-kg wooden crate reveals:\n- Farm-gate price received by grower: $6.00/crate.\n- Village assembler incurs $1.00 transport and $0.50 loading costs, selling to urban wholesaler at $9.00/crate.\n- Urban wholesaler incurs $1.20 cold storage and packaging costs, absorbing a 5% physical rot loss, and sells to supermarkets at $14.00/crate.\n- Supermarket displays tomatoes and retails them to consumers at $1.20 per kg ($24.00 per 20-kg crate equivalent).\n1) Calculate the total marketing margin (price spread) per crate.\n2) Calculate the Farmer's Share of the Consumer Dollar (%).\n3) Calculate the net profit earned by each actor (Grower assuming variable production cost of $3.50/crate, Assembler, Wholesaler, and Supermarket).\n\nSolution:\n1) Total Marketing Margin (Price Spread):\nMM = Retail Price (P_r) - Farm-Gate Price (P_f)\nMM = $24.00 - $6.00 = $18.00 per 20-kg crate.\n\n2) Farmer's Share of Consumer Dollar (FS):\nFS = (P_f / P_r) x 100 = ($6.00 / $24.00) x 100 = 25.0%.\n(Marketing intermediaries capture 75.0% of consumer expenditure).\n\n3) Net Profit Breakdown per Crate:\n- Grower Net Profit = $6.00 - $3.50 (cost) = $2.50/crate.\n- Assembler Net Profit = $9.00 - ($6.00 purchase + $1.00 transport + $0.50 loading) = $9.00 - $7.50 = $1.50/crate.\n- Wholesaler Revenue = $14.00 x 0.95 (5% loss) = $13.30. Wholesaler Net Profit = $13.30 - ($9.00 purchase + $1.20 storage) = $13.30 - $10.20 = $3.10/crate.\n- Supermarket Net Profit = $24.00 - $14.00 purchase = $10.00 gross margin per crate (minus operating store overhead)."
                    ),
                    commonExamQuestions = listOf(
                        "Identify the four economic marketing utilities and provide concrete agricultural examples of each.",
                        "Explain why marketing margins are generally much higher for perishable horticultural crops than for non-perishable cereal grains.",
                        "Describe the operational mechanism and producer economic benefits of a Warehouse Receipt System (WRS)."
                    ),
                    practicalTakeaway = "Form agricultural producer cooperatives to aggregate harvest volumes: volume aggregation transforms smallholders from weak price-takers into collective price-negotiators and cuts per-unit transport costs in half.",
                    authoritativeTextbookRef = "Marketing of Agricultural Products (Kohls & Uhl, 9th Ed., Prentice Hall, Ch. 1-3, 9); Agricultural and Food Marketing Management (FAO, Rome)"
                )
            ),
            examFocusPoints = listOf(
                "Calculation of Marketing Margins, Price Spreads, and Farmer's Share of Consumer Dollar",
                "The four marketing utilities: Form, Place, Time, and Possession",
                "Impact of input subsidies vs. price floors on agricultural market equilibrium",
                "Keynesian multiplier and agricultural terms of trade calculations"
            ),
            practicalFieldApplications = listOf(
                "Calculating marketing price spreads across supply chain nodes from farm-gate to urban supermarket",
                "Conducting farm enterprise sensitivity analysis against fuel inflation and interest rate hikes",
                "Designing warehouse receipt financing frameworks for smallholder farmer cooperatives"
            ),
            recommendedTextbookIds = listOf("book_kohls_uhl_agri_marketing", "book_drummond_agri_economics")
        )
    )
}
