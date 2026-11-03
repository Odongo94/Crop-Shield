# CropShield IPM 🌾🛡️

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org)
[![Android Gradle Plugin](https://img.shields.io/badge/AGP-8.5.0-green.svg)](https://developer.android.com/studio/releases/gradle-plugin)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com/about/dashboards)
[![Target SDK](https://img.shields.io/badge/Target%20SDK-36-blueviolet.svg)](https://developer.android.com/about/versions/16)
[![License](https://img.shields.io/badge/License-Apache%202.0-lightgrey.svg)](LICENSE)

[![Direct APK Download](https://img.shields.io/badge/Download%20APK-CropShield--v4.apk-success?style=for-the-badge&logo=android)](https://github.com/odongovincento/cropshield-ipm/releases/download/v4/CropShield-v4.apk)
[![GitHub Release](https://img.shields.io/github/v/release/odongovincento/cropshield-ipm?style=for-the-badge&logo=github)](https://github.com/odongovincento/cropshield-ipm/releases)

---

### 📲 Direct Download Links (Version v4)
* 👉 **[Browse All Releases on GitHub (Guaranteed Safe / No 404)](https://github.com/odongovincento/cropshield-ipm/releases)**
* 👉 **[Direct Download CropShield-v4.apk (v4 Release)](https://github.com/odongovincento/cropshield-ipm/releases/download/v4/CropShield-v4.apk)**
* 👉 **[Download Latest Auto-Updating APK](https://github.com/odongovincento/cropshield-ipm/releases/latest/download/CropShield-latest.apk)**

> 💡 **Troubleshooting: If GitHub displays "404 - This is not the web page you are looking for"**:  
> 1. Always open the **[Releases Overview Page](https://github.com/odongovincento/cropshield-ipm/releases)** — this page never 404s and lists all published versions (`v3`, `v4`).  
> 2. **Attach the APK to GitHub**: GitHub releases only package `.zip` source code by default until an `.apk` is uploaded. To attach it in 30 seconds: on GitHub, click your release $\rightarrow$ Click **Edit** $\rightarrow$ Drag & drop `CropShield-v4.apk` (or `app-debug.apk`) into the **"Attach binaries by dropping them here"** box $\rightarrow$ Click **Update release**.  
> 3. Once attached, direct links activate immediately for anyone around the world!

---

### 💬 Shareable Download Link (Copy & Send to Farmers / Scouts)

> **🌾 CropShield IPM v4 (Android Precision Agronomy App)**  
> Direct APK Download:  
> https://github.com/odongovincento/cropshield-ipm/releases/download/v4/CropShield-v4.apk  
>   
> GitHub Releases Portal:  
> https://github.com/odongovincento/cropshield-ipm/releases  
>   
> *Includes USDA Soil Texture Calculator, Crop Rotation Matrix, 16L–2000L Knapsack & Tractor Tank Dosing, Offline Satellite Farm GIS Mapping, Agricultural Entomology Pest Scanner, and Google Scholar Journals.*  
> *Signed off by Crop Scientist Odongo Vincent.*

---

> **Offline crop agronomy, agricultural entomology pest diagnostics, weed bio-indicator soil diagnostician, crop pathology, precision scouting sheets, GPS field mapping, soil classification calculator, Google Scholar scientific journals, and crop rotation suite signed off by Crop Scientist Odongo Vincent.**

---

## 📖 Overview

**CropShield IPM** is an advanced, offline-first precision agriculture and Integrated Pest Management (IPM) Android application developed for agronomists, field scouts, extension officers, and commercial growers. Designed specifically to operate reliably in rural and off-grid farming environments, CropShield delivers evidence-based agronomic intelligence, mathematical dosing calculations, satellite GIS mapping, and soil classification tools without requiring constant cellular connectivity.

---

## 📱 How to Download and Install on Android Phone

Follow these 4 simple steps to install CropShield on any Android device (or share with your scouting team):

### Step 1: Download the APK
On your phone's browser (Chrome, Samsung Internet, Firefox), tap the direct download link:  
👉 **[Download CropShield-v4.apk](https://github.com/odongovincento/cropshield-ipm/releases/download/v4/CropShield-v4.apk)**

> *Note: If Android displays a warning: `"File might be harmful. Do you want to download CropShield-v4.apk anyway?"`, tap **"Download anyway"**. This standard system notice appears for all applications installed directly outside the Google Play Store.*

### Step 2: Open Downloaded File
* When the download completes, tap **"Open"** from your browser notification tray.
* Or open your phone's **Files** or **Downloads** app and tap `CropShield-v4.apk`.

### Step 3: Allow Install from This Source (First-Time Only)
If Android prompts: `"For your security, your phone is not allowed to install unknown apps from this source"`:
1. Tap **Settings** on the prompt.
2. Toggle ON **"Allow from this source"**.
3. Tap the **Back** arrow to return to the installer.

### Step 4: Tap Install (or Update)
* Tap **"Install"** (or **"Update"** if upgrading from v3 or earlier).
* Once finished, tap **"Open"** to launch CropShield IPM!

---

## 🔄 Version Management & Seamless Subsequent Updates (`v4` $\rightarrow$ `v5`, `v6`...)

### How Subsequent Updates Work
When you install subsequent versions (e.g. updating from `v3` to `v4`, or later to `v5`):
* Simply download and tap the new APK (`CropShield-v4.apk`). Android recognizes the existing application package and prompts **"Do you want to update this app?"**
* Tap **"Update"**.
* **Zero Data Loss**: All your saved scouting records, farm field boundaries, GPS hectares, fertilizer history, and offline notes stored in the local SQLite/Room database remain **100% intact**.

### How to Release Version v4 (and Future Versions) on GitHub
The repository includes an automated GitHub Actions CI/CD pipeline (`.github/workflows/release.yml`). To assign `v4` and publish subsequent updates:

1. **Push a Git Version Tag:**
   ```bash
   # Assign and push the v4 release tag
   git tag v4
   git push origin v4
   ```

2. **Automated Build & Distribution:**
   * GitHub Actions automatically wakes up, checks out the repository, and runs `gradle assembleDebug`.
   * It creates a new release on GitHub under `Tags > v4`.
   * It automatically attaches `CropShield-v4.apk` and `CropShield-latest.apk` as downloadable assets.
   * Anyone visiting `https://github.com/odongovincento/cropshield-ipm/releases/latest` or using the direct links will immediately receive `v4`!

3. **Subsequent Versions (v5, v6, etc.):**
   When you're ready for the next release, simply run:
   ```bash
   git tag v5
   git push origin v5
   ```
   Or go to **GitHub > Releases > "Draft a new release"**, choose tag `v5`, and publish.

4. **Manual Workflow Dispatch (Alternative):**
   * Go to **GitHub > Actions > "Build & Release CropShield APK"**.
   * Click **"Run workflow"**, type `v4` (or `v5`), and click Run.

---

## ✨ Key Features

### 1. 🧪 Soil Classification Calculator & USDA Texture Triangle
* **Interactive USDA Texture Triangle**: Custom-engineered Jetpack Compose `Canvas` rendering all 12 USDA textural classes with distinct harmonic color fills and high-contrast boundaries.
* **Animated Pulsing Pointer**: Dynamic coordinate pin and ripple animation pointing directly to the classified coordinate based on user input.
* **Barycentric Touch & Drag**: Interactive touch input on the triangle canvas to explore soil textural classes and immediately read particle fractions.
* **Dual Input Modes**:
  * **Particle Percentage Sliders (%)**: Sliders for Sand, Silt, and Clay with real-time normalization and a 1-tap **"Auto-Balance to 100%"** utility.
  * **Sedimentation Jar Test (Field Method)**: Input measured layer heights (mm) of settled Sand (1-min), Silt (2-hr), and Clay (24-hr) to compute exact fractions.
* **Regional Soil Taxonomies**: Pre-calibrated presets for East African soils:
  * *Humic Nitisols* (Kikuyu Red Clays)
  * *Eutric Vertisols* (Black Cotton Soils)
  * *Volcanic Andosols* (Rift Valley Silt Loams)
  * *Arenosols & Ferralsols* (Coastal Sands & Sandy Loams)

### 2. 🌾 Tailored Agronomic Advisory Engine
* **Crop Suitability Matrix**: Classifies crops into *Highly Suitable*, *Moderate (requires raised beds or amendments)*, and *Unsuitable / High Risk* with clear agronomic reasoning.
* **Fertility & Dosing Management**:
  * Cation Exchange Capacity (CEC) rating and nutrient retention dynamics.
  * Basal placement guidelines to mitigate Phosphorus fixation in volcanic clays.
  * Split-N topdressing schedule to prevent nitrate leaching in sandy profiles.
  * Well-decomposed farmyard manure/compost rates (metric tonnes/ha).
  * Liming and soil conditioning protocols (Calcitic lime vs. Agricultural Gypsum for sodic Vertisols).
* **Water Application & Irrigation Scheduling**:
  * Infiltration rates ($\text{mm/hr}$) and Available Water Capacity (AWC in $\text{mm/m}$).
  * Irrigation system recommendations (low-rate drip, micro-sprinklers, furrow basins).
  * Pulse irrigation vs. deep watering intervals to avert waterlogging and root asphyxiation.
* **Routine Field Management Practices**:
  * Cultivation moisture workability window to eliminate plastic clods and hardpans.
  * Organic mulching standards and subsoiling/ripping intervals.
  * Routine laboratory soil testing schedules.

### 3. 🔄 Interactive Crop Rotation Pattern Designer
* **4-Season Multi-Year Matrix**: Interactive rotation builder across 4 agronomic plant families:
  1. **Heavy Feeders** (*Maize, Cabbage, Potatoes, Tomatoes, Sorghum*)
  2. **Legume Nitrogen Fixers** (*Common Beans, Peas, Cowpeas, Desmodium*)
  3. **Light Feeders & Root Crops** (*Carrots, Onions, Garlic, Sweet Potatoes*)
  4. **Soil Conditioners & Cover Crops** (*Sunn Hemp, Forage Oats, Brachiaria, Vetch*)
* **Diagnostic Sustainability Scorecard**:
  * **Pest & Pathogen Break Index (0–100)**: Evaluates non-host succession.
  * **Biological Nitrogen Balance (0–100)**: Computes legume Rhizobia replenishment vs. heavy feeder extraction.
  * **Weed & Soil Structure Conditioning (0–100)**: Analyzes root bio-drilling and canopy weed smothering.
* **Critical Pathogen Alerts**: Flags consecutive Solanaceae plantings (e.g. Potatoes $\rightarrow$ Tomatoes) to break Late Blight (*Phytophthora infestans*) and Bacterial Wilt (*Ralstonia solanacearum*).
* **Pre-Validated Templates**:
  * *Commercial Vegetable & Pulse Cycle*
  * *Highland Cereal & Tuber Sequence*
  * *Grain, Pulse & Fodder System*
  * *Nematode Bio-Fumigation Brassica Cycle*

### 4. 🧮 Knapsack & Commercial Tank Dilution Calculator
* **Multi-Volume Support**: Supports standard 16L–20L backpack knapsacks up to commercial 500L, 1000L, 1500L, and 2000L tractor spray rigs and boom sprayers.
* **Dual Calculation Modes**:
  * **Fertilizer Dosing**: Basal, topdressing, and foliar spray tank allocations in grams, kilograms, milliliters, and liters.
  * **Pesticide / Fungicide Dilution**: Dilution rate calculator with water carrier volume, application rates, pre-harvest intervals (PHI), and re-entry intervals (REI).

### 5. 🗺️ Offline GPS Field Mapping & Google Satellite GIS
* **Satellite Base Layer**: High-resolution Google Satellite imagery stream with intelligent disk and LRU memory caching for offline field viewing.
* **Perimeter Walk & Pin Drop**: Walk boundaries using real-time GPS tracking or drop pins to measure farm area in Hectares and Acres.
* **Direct Knapsack Linking**: Instantly transfer mapped acreage into the Knapsack Calculator.

### 6. 🔍 IPM Agricultural Entomology & Pathology Scanner
* **Factual Entomology & Pathology Separation**: Specialized multimodal vision and optical diagnostic engine that strictly distinguishes between **Insect Pests** (and their characteristic feeding: whorl chewing, blotch mines, silvering, silk webbing, bore frass, honeydew colonies) and **Plant Diseases** (fungal, bacterial, oomycete).
* **Comprehensive Pest Diagnostic Coverage**: Accurately identifies *Fall Armyworm* (Spodoptera frugiperda), *Tomato Leafminer* (Tuta absoluta), *Two-Spotted Spider Mites* (Tetranychus urticae), *Western Flower Thrips* (Frankliniella occidentalis), *African Bollworm* (Helicoverpa armigera), *Diamondback Moth* (Plutella xylostella), *Aphids*, *Whiteflies*, and *Maize Stem Borers*.
* **Integrated 3-Tier IPM Protocol**: Provides Infonet-Biovision cultural remedies, CABI BioProtection registered biological agents, and PCPB Kenya approved agrochemical active ingredients with specific dilution rates.
* **Disease Lifecycle Lab**: Interactive 4-stage pathogenesis visualizer (Inoculation, Incubation, Symptom Expression, Overwintering) detailing optimal chemical/biological intervention windows.

### 7. 📓 Editable Field Agronomy Notebook & Column-Based Diary
* **Notebook Structure with Dedicated Entry Columns**: Formatted as a real agricultural field notebook with structured columns for **Date & Time**, **Plot / Block Location**, **Crop & Variety**, **Phenological Growth Stage**, **Observed Pest / Disease / Symptom**, **Severity Level**, **Soil / Moisture Condition**, **Action / Treatment Taken**, and **Field Remarks**.
* **100% User Editable Anytime**: Users can tap any notebook entry or table row to edit any column value at their convenience, with instant local Room SQLite persistence.
* **Dual Viewing Formats**:
  * **Lined Notebook Pages**: Authentic notebook layout with page numbering, binder margins, column badges, and quick-edit controls.
  * **Multi-Column Ledger Table**: Horizontally scrollable farm ledger table displaying all entry columns side-by-side with 1-tap row editing.
* **1-Tap CSV Notebook Export & Sharing**: Instantly generate and share a full column-formatted CSV file of all field observations via WhatsApp, email, or cloud drive.

### 8. 📚 Google Scholar Scientific Research & Crop Production Library
* **Google Scholar Direct Research**: Seamlessly browse peer-reviewed agronomic papers directly within each crop profile. Tapping any journal opens the citation directly on Google Scholar.
* **Interactive Scientific Search Bar**: Query Google Scholar for any agricultural topic, pest resistance study, or soil fertility kinetic paper directly from the library.
* **Institutional Evidence Base**: Papers curated from KALRO (Kenya Agricultural & Livestock Research Organization), ICIPE (International Centre of Insect Physiology and Ecology), CABI, CIMMYT, Elsevier, and Springer Nature.

### 9. 🎓 Agriculturist Licensure & Academic Learning Suite (6 Core Areas)
* **Complete 6-Pillar Licensure Syllabus**: Directly integrates the official syllabus curriculum for Professional Agriculturists with detailed scientific elaborations, biochemical mechanisms, practical takeaways, and exam focus points:
  1. **Crop Science**:
     * *Agronomy*: Cereals & Field Legumes, Root & Tuber Crops, Seed Physiology, Industrial Crop Production
     * *Horticulture*: Pomology (fruit science & chilling requirements), Olericulture (vegetable physiology & greenhouse fertigation), Floriculture (export cut flowers & pulsing solutions), Landscape Management (CODIT pruning & hydrozoning)
     * *Plant Breeding and Genetics*: Quantitative inheritance, Breeder's Equation ($R = h^2 \times S$), cytoplasmic male sterility, heterosis, and marker-assisted selection
     * *Farming Systems*: Land Equivalent Ratio ($\text{LER} > 1.0$), alley cropping, and Conservation Agriculture
     * *Agricultural Biotechnology*: In vitro plant tissue culture (auxin:cytokinin organogenesis ratios), transgenic Bt Cry endotoxins, and CRISPR/Cas9
     * *Crop Physiology & Ecology*: C3 vs. C4 Kranz anatomy, SPAC water potential gradients ($\Psi_w = \Psi_s + \Psi_p + \Psi_m$), and photorespiration
     * *Seed Technology & Production*: Breeder, Foundation, Registered, and Certified seed classes, Pure Live Seed (PLS), isolation distances, and ISTA testing
     * *Postharvest Management*: Climacteric vs. non-climacteric respiration kinetics, ethylene receptor blockade by 1-MCP, and modified atmosphere packaging
  2. **Soil Science**:
     * *Soil Genesis, Composition & Development*: Hans Jenny's 5 factors ($S = f(Cl, O, R, P, T)$), Goldich weathering series, and master horizon development ($O-A-E-B-C-R$)
     * *Soil Taxonomy, Classification & Land Use*: The 12 USDA Soil Orders (Vertisols, Andisols, Mollisols, Oxisols, etc.) and Land Capability Classes I–VIII
     * *Soil Microbiology*: C:N ratio mineralization-immobilization thresholds ($>30:1$ vs. $<20:1$), biological nitrogen fixation, and mycorrhizal AMF hyphal networks
     * *Soil Physics*: Stokes' law sedimentation, bulk density ($D_b$), porosity, Available Water Capacity (AWC), and Darcy's law
     * *Soil Fertility and Management*: Liebig's Law of the Minimum, 4R nutrient stewardship, nutrient mobility diagnoses, and acid soil liming chemistry
     * *Soil Chemistry*: Cation Exchange Capacity (CEC), Percentage Base Saturation (%BS), Lyotropic exchange affinity series, and Gypsum reclamation of sodic soils ($ESP > 15\%$)
  3. **Animal Science**:
     * *Animal Breeding & Genetics*: Heritability ($h^2$), Estimated Breeding Values (EBVs), inbreeding depression, and terminal crossbreeding
     * *Animal Physiology*: Neuroendocrine regulation of estrous cycles (GnRH, LH, FSH, Progesterone, PGF2α), oxytocin milk letdown, and thermoregulation
     * *Animal Nutrition*: Ruminant fermentation (Acetate, Propionate, Butyrate VFAs), Weende proximate analysis, Van Soest NDF/ADF fiber systems, and Pearson Square ration balancing
     * *Animal Products Processing*: Rigor mortis and glycolysis curves, prevention of PSE and DFD meat conditions, HTST milk pasteurization ($72^\circ\text{C}$ for 15s), and Haugh Unit egg grading
     * *Animal Health and Diseases*: Biosecurity exclusion, Anthrax protocols, African Swine Fever (ASF), Foot-and-Mouth Disease (FMD), and California Mastitis Test (CMT)
     * *Poultry Production*: Broiler brooding curves, layer 16-hour lighting regimens, incubation physical parameters, and Feed Conversion Ratio (FCR)
     * *Swine Production*: Sow farrowing crate management, 114-day gestation, and neonatal iron dextran injection
     * *Ruminant Production*: (i) Dairy & Meat Goat management with FAMACHA selective deworming, (ii) Sheep flushing and copper toxicity prevention, (iii) Bovine colostrum feeding rules (10% bodyweight within 6 hrs) and transition cow ketosis, (iv) Carabao / Water Buffalo (*Bubalus bubalis*) draft physiology, wallowing thermoregulation, and high-butterfat (8%) dairy production
  4. **Crop Protection**:
     * *Plant Pathology*: Bacteriology (Ralstonia stem streaming test & T3SS), Mycology (Oomycetes vs. True Fungi & appressorium mechanics), Nematology (Root-knot *Meloidogyne* giant cells & biofumigation), and Virology (+ssRNA, stylet-borne non-persistent vs. persistent circulative transmission)
     * *Entomology*: Insect mouthpart adaptations, complete (holometabolous) vs. incomplete (hemimetabolous) metamorphosis, Economic Threshold (ET), and Economic Injury Level ($EIL = C / [V \times I \times D \times K]$)
     * *Weed Science*: Botanical differentiation (Grasses, Sedges, Broadleaves), Critical Period of Weed Control (CPWC), and HRAC herbicide resistance rotation
     * *Management of Plant Diseases*: The Disease Triangle & Tetrahedron, Whetzel's 5 principles (Exclusion, Eradication, Protection, Resistance, Therapy), and FRAC resistance codes
     * *Problems in Insecticide & Dosing Calculations*: Sprayer application volume formula ($\text{L/ha} = [\text{L/min} \times 600] / [\text{km/h} \times \text{swath m}]$), active ingredient percentage dilution, and knapsack tank dosage calculations
  5. **Agricultural Economics & Marketing**:
     * *Microeconomics & Elasticities*: Price elasticity of demand ($E_d$), Engel's Law, King's Law farmer paradox, and agricultural price volatility
     * *Production Analysis & Cost Curves*: Neoclassical production function, rational economic operating Stage II ($MPP < APP$ and $MPP > 0$), profit maximization ($MR = MC$, $VMP = MFC$), shutdown point ($P < \min AVC$), and break-even point ($P = \min ATC$)
     * *Macroeconomics & Agricultural Policy*: National income accounting (GDP contribution), monetary policy interest rate impacts, and fertilizer subsidy welfare effects
     * *Agricultural Marketing*: The four marketing utilities (Form, Place, Time, Possession), marketing margins, and Farmer's Share of the consumer dollar
  6. **Agricultural Extension & Communications**:
     * *Philosophy & Andragogy*: 'Helping people help themselves', Malcolm Knowles' adult learning principles, and Edgar Dale's Cone of Experience (90% retention through doing)
     * *Development Communication & Commodity Policy*: Participatory Rural Appraisal (PRA) transect walks and seasonal calendars, and price stabilization policies
     * *Extension Methods*: Individual visits, Group Farmer Field Schools (FFS), Method Demonstrations vs. Result Demonstrations, and mass digital mobile advisory
     * *Communication Models*: David Berlo's SMCRF model (Source, Message, Channel, Receiver, Feedback) and semantic communication noise
     * *Innovation Adoption Process*: The 5 AIETA stages (Awareness, Interest, Evaluation, Trial, Adoption) and Everett Rogers' 5 Adopter Categories (Innovators 2.5%, Early Adopters 13.5%, Early Majority 34%, Late Majority 34%, Laggards 16%)
* **📚 Authoritative Academic Book Library**: Curated repository of classic and standard university textbooks (Brady & Weil's *The Nature and Properties of Soils*, Agrios' *Plant Pathology*, Taiz & Zeiger's *Plant Physiology*, Ensminger's *Animal Science*, Pedigo's *Entomology and Pest Management*, Drummond's *Agricultural Economics*, Rogers' *Diffusion of Innovations*, and more) complete with core chapter breakdowns and 1-tap Google Scholar academic research queries.
* **✍️ Interactive Licensure Practice Exam**: Comprehensive multiple-choice board examination simulation with instant feedback, scientific explanations, and citations to recommended textbooks.

### 10. ⚡ Soil Cation Exchange Capacity (CEC) & Albrecht BCSR Rebalancing Calculator
* **Dual Diagnostic Evaluation Modes**:
  * **Laboratory Soil Cations Entry**: Input measured exchangeable cations from laboratory reports ($Ca^{2+}, Mg^{2+}, K^+, Na^+$ in meq/100g or cmol(+)/kg) and exchangeable acidity ($Al^{3+} + H^+$).
  * **Colloidal Particle Pedotransfer Estimation**: Calculate estimated effective CEC from soil texture and organic matter using the pedotransfer equation:
    $$\text{CEC}_{\text{est}} = (0.05 \times \%\,\text{Sand}) + (0.15 \times \%\,\text{Silt}) + (0.65 \times \%\,\text{Clay}) + (2.0 \times \%\,\text{Organic Matter})$$
* **Albrecht Base Cation Saturation Ratio (BCSR) Audit**:
  * Calculates Total Cation Exchange Capacity ($\text{CEC} = \sum \text{Cations}$).
  * Computes Base Saturation ($\%\,\text{BS} = \frac{Ca + Mg + K + Na}{\text{CEC}} \times 100$).
  * Compares actual saturation percentages against ideal target intervals:
    * Calcium Saturation: **65% – 75%**
    * Magnesium Saturation: **10% – 15%**
    * Potassium Saturation: **3% – 5%**
    * Sodium Saturation: **$< 1.5\%$** (Identifies sodicity hazard if $> 5\%$)
    * Exchangeable Acidity: **$< 5\%$** (Alkaline/neutral) vs. Toxic Aluminum saturation
* **Exact Cation Balance Ratios**:
  * **$Ca:Mg$ Ratio**: Target $5:1$ to $7:1$. Flags soil dispersion and crusting if $< 3:1$, or induced magnesium deficiency if $> 10:1$.
  * **$Mg:K$ Ratio**: Target $2:1$ to $3:1$. Flags potassium uptake blockages if $> 4:1$.
* **Prescriptive Soil Amendment Dosing (kg/ha and kg/acre)**:
  * Calculates exact application weights for **Agricultural Lime** ($\text{CaCO}_3$), **Dolomitic Limestone** ($\text{CaMg}(\text{CO}_3)_2$), **Gypsum** ($\text{CaSO}_4 \cdot 2\text{H}_2\text{O}$), **Sulfate of Potash** ($\text{K}_2\text{SO}_4$), **Humic Compost**, and **Biochar**.
  * Complete step-by-step mathematical derivations displayed in the UI.

### 11. 🌿 Organic Manure Masterclass & Crop Tonnage Calculator
* **Precision Crop Manure Tonnage Engine**:
  * Computes exact dry-matter and moist tonnage requirements per hectare and acre across 10 commercial crops (Tomatoes, Irish Potatoes, Maize, French Beans, Brassicas, Onions, Coffee, Avocados, Bananas, Cut Roses).
  * Calculates the exact number of **50 kg standard manure bags** and **grams/kg per planting hole/tree station**.
  * Predicts 1st-year available Nitrogen ($\text{kg N/ha}$), Phosphorus ($\text{kg P}_2\text{O}_5\text{/ha}$), and Potassium ($\text{kg K}_2\text{O/ha}$) supplied via mineralization equations:
    $$\text{Tons}_{\text{req}} = \frac{\text{Crop N Demand (kg/ha)}}{\text{Total N in Manure (kg/t)} \times \text{Year 1 Mineralization Rate}} \times \text{Soil OM Correction Factor}$$
* **Standard Operating Procedures (SOPs) for 5 Composting Methods**:
  1. **18-Day Berkeley Hot Composting Method**: Rapid thermophilic compost in 18 days with precise turning schedules on days 4, 6, 8, 10, 12, 14.
  2. **Aerobic Windrow Composting**: Large-scale farm waste windrows ($1.5\text{ m}$ height) with trapezoidal cross-sections.
  3. **Indore Heap & Pit Composting**: Traditional low-input layer system (Browns, Greens, Animal Dung, Wood Ash/Topsoil) with central aeration chimney.
  4. **Vermicomposting (Earthworm Farming)**: Epigeic earthworm cultivation (*Eisenia fetida* / Red Wigglers) for biological casting harvest and aerated worm tea.
  5. **Bokashi Anaerobic Fermentation**: Japanese lactic acid fermentation using EM-1 (Effective Microorganisms), molasses, and wheat bran under airtight seal.
* **Raw Materials Database with C:N Ratios**:
  * Detailed catalog distinguishing Brown (Carbon-rich) and Green (Nitrogen-rich) materials with their specific C:N ratios, moisture contents, and preparation handling tips.
* **Compost Management & Quality Standards**:
  * **Moisture Management**: The Agronomic Squeeze "Fist Test" with diagnostic indicators (Too Dry, Optimal 50-60%, Waterlogged Anaerobic).
  * **Thermophilic Temperature Curve**: Phase 1 Mesophilic ($25\text{--}40^\circ\text{C}$), Phase 2 Thermophilic ($55\text{--}65^\circ\text{C}$ for pathogen/weed seed pasteurization), Phase 3 Cooling, and Phase 4 Curing.
  * **Maturity & Phytotoxicity Tests**: Earthy forest aroma, dark brown crumble, and cress seed germination bioassay ($>85\%$ germination).

### 12. 💧 FAO-56 Irrigation Water Requirement & Soil/Water EC-pH Harmonizer
* **FAO-56 Penman-Monteith Evapotranspiration Engine**:
  * Calculates Crop Evapotranspiration: $\text{ET}_c = \text{ET}_0 \times K_c$
  * Factors in effective rainfall: $\text{Net Irrigation} = \max(0, \text{ET}_c - \text{Rainfall}_{\text{eff}})$
  * Supports 11 crops across 4 phenological growth stages (Initial, Crop Development, Mid-Season Peak, Late Season/Harvest).
* **Leaching Fraction (LF) & Salinity Control**:
  * Evaluates water salinity ($\text{EC}_w$) and soil salinity ($\text{EC}_e$).
  * Calculates required leaching fraction using the FAO-29 / Rhoades equation:
    $$\text{LF} = \frac{\text{EC}_w}{5 \times \text{EC}_e - \text{EC}_w}$$
  * Adjusts Gross Water Requirement for system application efficiency:
    $$\text{Gross Depth (mm/day)} = \frac{\text{Net Irrigation}}{\text{Efficiency} \times (1 - \text{LF})}$$
* **Volumetric Water Deliveries**:
  * Displays Gross Volume in $\text{m}^3/\text{ha}/\text{day}$, $\text{m}^3/\text{acre}/\text{day}$, and precise **Liters per plant per day** tailored to crop plant density.
  * Recommends optimal irrigation intervals (days) based on soil texture Available Water Capacity (AWC) and root zone depletion fractions ($p$).
* **Soil & Irrigation Water pH Interaction Matrix**:
  * Highlights nutrient availability locks at acid ($<5.5$), optimal ($6.0\text{--}6.8$), and alkaline ($>7.5$) ranges.
  * **Water Acidification Dosing Calculator**: Computes exact milliliters of **Phosphoric Acid 85%**, **Nitric Acid 68%**, or grams of **Citric Acid** needed per $1,000\text{ Liters}$ of water to neutralize excess bicarbonates ($\text{HCO}_3^-$) and bring pH to optimal $5.8\text{--}6.2$.

### 13. 🥗 Comprehensive Phenological Crop Feeding Regimes & Drip Fertigation Schedules
* **Complete Nutrient Demand per Metric Ton**:
  * Exact kg of $\text{N}, \text{P}_2\text{O}_5, \text{K}_2\text{O}, \text{CaO}, \text{MgO}, \text{S}$, and micronutrients consumed per metric ton of harvest.
* **Phenological Split Feeding Stages**:
  * Stage 1: **Basal / Planting** (Root development with high P, mycorrhizal inoculation)
  * Stage 2: **Vegetative Establishment** (Canopy and leaf area index with balanced N and micronutrients)
  * Stage 3: **Flowering & Fruit Set** (Pollen viability, blossom-end rot prevention with Boron and chelated Calcium)
  * Stage 4: **Fruit Bulking & Tuber Filling** (Potassium-driven sugar translocation and starch synthesis)
  * Stage 5: **Maturity & Post-Harvest Reconditioning** (Ripening, brix accumulation, hardening)
* **Drip Fertigation & Foliar Booster Recipes**:
  * Weekly fertigation injection formulas ($\text{g/plant/week}$ and $\text{kg/ha/week}$).
  * Rootzone electrical conductivity target ranges ($\text{EC}$ in $\text{mS/cm}$) and pH targets.
  * Visual symptom guides for common deficiencies and emergency corrective foliar sprays.

---

## 🛠️ Architecture & Tech Stack

CropShield IPM is engineered adhering to modern Android development best practices:

* **Language**: [Kotlin](https://kotlinlang.org) (100%)
* **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material Design 3 (M3)
* **Architecture**: Model-View-ViewModel (MVVM) + Clean Architecture
* **State Management**: Kotlin Coroutines & `MutableStateFlow` with `collectAsStateWithLifecycle`
* **Local Persistence**: [Room Database](https://developer.android.com/training/data-storage/room) with KSP (Kotlin Symbol Processing)
* **Custom Graphics**: Jetpack Compose `Canvas` with Barycentric ternary coordinate transformation
* **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
* **Testing**: [Robolectric](https://robolectric.org/) and JUnit 4 for local JVM unit and integration testing

---

## 📁 Project Structure

```
app/src/main/java/com/example/
├── MainActivity.kt                       # Single-activity container & navigation routing
├── data/
│   ├── AgronomyCatalog.kt               # Fertilizer plans, spray rates, knapsack math
│   ├── CropProductionCatalog.kt         # Comprehensive crop production database
│   ├── ThreatCatalog.kt                 # Factual pests, diseases, and IPM solutions
│   ├── local/                           # Room Database, DAOs, and Entity models
│   ├── scanner/                         # Photo diagnosis and botanical symptom matcher
│   ├── science/                         # Research publications, journals, and learning quiz
│   ├── soil/
│   │   └── SoilClassificationCatalog.kt # USDA classification algorithm, profiles & rotation templates
│   └── weather/                         # 47 Kenya counties weather repository & GPS detection
├── model/
│   ├── CropThreat.kt                    # Pest, disease, and IPM data models
│   ├── KnapsackModels.kt                # Spray tank and chemical formulation models
│   ├── WeedModel.kt                     # Weed species and herbicide timing models
│   └── soil/
│       └── SoilClassificationModel.kt   # USDA classes, coordinates, fertility, water & rotation models
├── ui/
│   ├── components/
│   │   ├── CropRotationDesignerComponent.kt # Interactive 4-season rotation pattern builder
│   │   ├── DiseaseLifecycleStepper.kt       # Pathogenesis stage visualizer
│   │   ├── KnapsackCalculatorComponent.kt   # 16L - 2000L tank calculation component
│   │   ├── OfflineTileProvider.kt           # Google Satellite tile cache provider
│   │   ├── SoilTextureTriangleCanvas.kt     # USDA equilateral ternary triangle canvas
│   │   └── UniversalSearchDialog.kt         # Global cross-catalog search dialog
│   ├── screens/
│   │   ├── AgronomyScreen.kt                # Crop guides, weed diagnostic & weather suite
│   │   ├── CalculatorScreen.kt              # Knapsack, pesticide dilution & soil classifier tabs
│   │   ├── DiagnoseScreen.kt                # Symptom key diagnostician
│   │   ├── FieldMapScreen.kt                # Satellite GIS & farm boundary polygon mapper
│   │   ├── FieldNotesScreen.kt              # Room-backed scouting notes & observations
│   │   ├── LibraryScreen.kt                 # Searchable A-Z pest & disease library
│   │   ├── LifecycleLabScreen.kt            # Pathology lifecycle laboratory
│   │   ├── PhotoDiseaseScannerScreen.kt     # Visual camera scanner interface
│   │   ├── ScoutLogScreen.kt                # Field scouting log entries
│   │   ├── ScoutingSheetScreen.kt           # Standardized scouting tables & CSV export
│   │   ├── ThreatDetailScreen.kt            # Deep-dive IPM threat view
│   │   ├── science/                         # Agricultural research journals & science quiz
│   │   └── soil/
│   │       └── SoilClassificationScreen.kt  # 3-tab Soil Triangle, Advisory & Rotation Designer
│   └── theme/                               # Material 3 ColorScheme, Typography & AppThemeMode
└── viewmodel/
    └── CropShieldViewModel.kt               # Centralized state management & UI state flows
```

---

## 📐 Soil Texture Triangle Mathematics

The USDA Soil Texture Triangle represents a 3-component ternary simplex where:
$$\text{Sand}\% + \text{Silt}\% + \text{Clay}\% = 100\%$$

### Coordinate Transformation
Given triangle vertices $V_{\text{sand}} = (x_{bl}, y_b)$, $V_{\text{silt}} = (x_{br}, y_b)$, and $V_{\text{clay}} = (x_{top}, y_t)$, any soil coordinate is projected onto the 2D Canvas via Barycentric linear interpolation:
$$X = \frac{\text{Sand}}{100} x_{bl} + \frac{\text{Silt}}{100} x_{br} + \frac{\text{Clay}}{100} x_{top}$$
$$Y = \frac{\text{Sand}}{100} y_b + \frac{\text{Silt}}{100} y_b + \frac{\text{Clay}}{100} y_t$$

### Barycentric Inversion on Touch
When a user taps or drags on the Canvas at $(x, y)$, normalized weights are inverted:
$$w_{\text{clay}} = \frac{y_b - y}{y_b - y_t}$$
$$w_{\text{silt}} = \frac{(x - x_{bl})(y_b - y_t) - (y_b - y)(x_{top} - x_{bl})}{(x_{br} - x_{bl})(y_b - y_t)}$$
$$w_{\text{sand}} = 1.0 - w_{\text{clay}} - w_{\text{silt}}$$

---

## 🚀 Getting Started

### Prerequisites
* **Android Studio**: Ladybug (2024.2.1) or newer
* **JDK**: Java 11 or Java 17
* **Android SDK**: `minSdk 24`, `compileSdk 36`
* **Gradle**: 8.5+

### Installation & Build

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/cropshield-ipm.git
   cd cropshield-ipm
   ```

2. **Open in Android Studio:**
   - Launch Android Studio and select **Open**.
   - Navigate to the cloned folder and let Gradle sync dependencies.

3. **Build the Debug APK via CLI:**
   ```bash
   gradle :app:assembleDebug
   ```

4. **Run Unit Tests:**
   ```bash
   gradle :app:testDebugUnitTest
   ```

5. **Install on Device:**
   Connect your Android device via USB debugging and run:
   ```bash
   gradle :app:installDebug
   ```

---

## 🧪 Testing

CropShield utilizes **Robolectric** for fast, deterministic local JVM testing of Critical User Journeys (CUJs) and agronomic calculation logic:
* Unit tests verify mathematical dosing formulas across 16L–2000L volumes.
* USDA soil classification algorithm boundary tests confirm 100% classification coverage without gaps.
* Room Database DAOs and entities are verified using in-memory SQLite instances.

To run all unit tests:
```bash
gradle test
```

---

## 👨‍🌾 Technical Lead & Agronomic Sign-Off

* **Agronomy & Plant Science Lead**: **Odongo Vincent** (*Crop Scientist & Agronomist*)
* **Focus Areas**: Integrated Pest Management (IPM), Sustainable Soil Fertility, Tropical Pathology, and Field Scout Capacity Building.

---

## 📄 License

```
Copyright 2026 CropShield IPM Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
