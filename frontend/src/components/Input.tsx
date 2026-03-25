import React from "react";

interface InputProps {
  label: string;
  name: string;
  type?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  className?: string;
}

const Input: React.FC<InputProps> = ({
  label,
  name,
  type = "text",
  value,
  onChange,
  className = "ht-input"
}) => {
  return (
    <div className="ht-form-row">
      <label className="ht-label">{label}</label>
      <input
        className={className}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
      />
    </div>
  );
};

export default Input;