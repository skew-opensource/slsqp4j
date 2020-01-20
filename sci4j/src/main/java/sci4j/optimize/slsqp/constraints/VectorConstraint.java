package sci4j.optimize.slsqp.constraints;

import sci4j.optimize.slsqp.Jacobian;
import sci4j.optimize.slsqp.functions.Vector2MatrixFunc;
import sci4j.optimize.slsqp.functions.Vector2VectorFunc;

public final class VectorConstraint
{
    private double[] args;
    private ConstraintType constraintType;
    private Vector2VectorFunc constraintFunc;
    private Vector2MatrixFunc jacobian;

    public static class VectorConstraintBuilder
    {
        private ConstraintType constraintType;
        private Vector2VectorFunc constraintFunc;
        private Vector2MatrixFunc jacobian;
        private double[] args;

        public VectorConstraint.VectorConstraintBuilder withConstraintFunction(Vector2VectorFunc constraintFunc)
        {
            this.constraintFunc = constraintFunc;
            return this;
        }

        public VectorConstraint.VectorConstraintBuilder withConstraintType(ConstraintType constraintType)
        {
            this.constraintType = constraintType;
            return this;
        }

        public VectorConstraint.VectorConstraintBuilder withJacobian(Vector2MatrixFunc jacobian)
        {
            this.jacobian = jacobian;
            return this;
        }

        public VectorConstraint.VectorConstraintBuilder withArgs(double... args)
        {
            this.args = args;
            return this;
        }

        public VectorConstraint build()
        {
            final VectorConstraint vectorConstraint = new VectorConstraint();
            vectorConstraint.constraintType = this.constraintType;
            vectorConstraint.constraintFunc = this.constraintFunc;
            vectorConstraint.jacobian = this.jacobian;
            vectorConstraint.args = this.args;
            return vectorConstraint;
        }
    }

    private VectorConstraint()
    {
    }

    public ConstraintType getConstraintType()
    {
        return constraintType;
    }

    public double[][] getJacobian(double[] x)
    {
        if (jacobian == null)
        {
            return Jacobian.approxJacobian(x, constraintFunc, args);
        }
        else
        {
            return jacobian.apply(x, args);
        }
    }

    public double[] apply(double[] x)
    {
        return constraintFunc.apply(x, args);
    }
}
