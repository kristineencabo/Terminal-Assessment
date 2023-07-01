package employeeant;

public class Deductions {

    public static float computeSSS(float grossSalary) {
        float startSalaryRange = 3250;
        float contribution = 135;

        if (grossSalary < 3250) {
            return contribution;
        } else if (grossSalary >= 24750) {
            contribution = 1125;
            return contribution;
        } else {
            while (true) {
                float endSalaryRange = startSalaryRange + 500;
                contribution += 22.5;

                if (grossSalary >= startSalaryRange && grossSalary < endSalaryRange) {
                    return contribution;
                } else {
                    startSalaryRange = endSalaryRange;
                }
            }
        }
    }

    public static float computePhilHealth(float monthlyBasicSalary) {
        double contributionRate = 0.03;
        double totalContribution = 0;
        double totalEmployeeContribution = 0;

        if (monthlyBasicSalary <= 10000) {
            totalContribution = 300;
        } else if (monthlyBasicSalary <= 59999.99) {
            totalContribution = Math.min(1800, monthlyBasicSalary * contributionRate);
        } else {
            totalContribution = 1800;
        }

        totalEmployeeContribution = totalContribution / 2;

        return (float) totalEmployeeContribution;
    }

    public static float computePagIBIG(float monthlyBasicSalary) {
        double contribution = 0.01;
        double totalEmployeeContribution = 0;

        if (monthlyBasicSalary > 1500) {
            contribution = 0.02;
        }

        totalEmployeeContribution = monthlyBasicSalary * contribution;

        if (totalEmployeeContribution > 100) {
            totalEmployeeContribution = 100;
        }

        return (float) totalEmployeeContribution;
    }

    public static float computeWithholdingTax(float taxableIncome) {
        double withholdingTax = 0;

        if (taxableIncome <= 20832) {
            withholdingTax = 0;
        } else if (taxableIncome <= 33333) {
            double excess = taxableIncome - 20833;
            withholdingTax = excess * 0.2;
        } else if (taxableIncome <= 66667) {
            double excess = taxableIncome - 33333;
            withholdingTax = (2500 + (excess * 0.25));
        } else if (taxableIncome <= 166667) {
            double excess = taxableIncome - 66667;
            withholdingTax = (10833 + (excess * 0.3));
        } else if (taxableIncome <= 666667) {
            double excess = taxableIncome - 166667;
            withholdingTax = (40833.33 + (excess * 0.32));
        } else {
            double excess = taxableIncome - 666667;
            withholdingTax = (200833.33 + (excess * 0.35));
        }

        return (float) withholdingTax;
    }
}
